package horas.crazytrips.service;

import horas.crazytrips.model.AssentoJanelaDecorator;
import horas.crazytrips.model.Cliente;
import horas.crazytrips.model.GuiaTuristicoDecorator;
import horas.crazytrips.model.PrimeiraClasseDecorator;
import horas.crazytrips.model.Viagem;
import horas.crazytrips.model.ViagemBase;
import horas.crazytrips.model.ViagemComponent;
import horas.crazytrips.model.ViagemFactory;
import horas.crazytrips.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final ClienteService clienteService;

    @Autowired
    public ViagemService(ViagemRepository viagemRepository, ClienteService clienteService) {
        this.viagemRepository = viagemRepository;
        this.clienteService = clienteService;
    }

    public List<Viagem> findAll() {
        return viagemRepository.findAll();
    }

    public Viagem findById(Integer id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada com id: " + id));
    }

    public Viagem save(Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    public void delete(Viagem viagem) {
        viagemRepository.delete(viagem);
    }

    public void deleteById(Integer id) {
        viagemRepository.deleteById(id);
    }

    public Viagem criarNovaViagem(String tipoViagem, String origem, String destino, double preco,
                                  LocalDate dataPartida, LocalDate dataChegada, String[] opcoes, Integer clienteId) {
        Viagem viagem = ViagemFactory.criarViagem(tipoViagem);
        viagem.setOrigem(origem);
        viagem.setDestino(destino);
        viagem.setPreco(preco);
        viagem.setDataPartida(dataPartida);
        viagem.setDataRetorno(dataChegada);

        if (clienteId != null) {
            Cliente cliente = clienteService.findById(clienteId);
            viagem.setCliente(cliente);
        }

        // Calculate price using decorator pattern
        if (opcoes != null && opcoes.length > 0) {
            ViagemComponent viagemComponent = new ViagemBase(viagem);

            for (String opcao : opcoes) {
                switch (opcao) {
                    case "primeira_classe":
                        viagemComponent = new PrimeiraClasseDecorator(viagemComponent);
                        viagem.setPrimeiraClasse(true);
                        break;
                    case "assento_janela":
                        viagemComponent = new AssentoJanelaDecorator(viagemComponent);
                        viagem.setAssentoJanela(true);
                        break;
                    case "guia_turistico":
                        viagemComponent = new GuiaTuristicoDecorator(viagemComponent);
                        viagem.setGuiaTuristico(true);
                        break;
                }
            }

            // Set the final price after applying all decorators
            viagem.setPreco(viagemComponent.getPreco());
        }

        return viagemRepository.save(viagem);
    }

    // Helper method to apply decorators based on viagem options
    public ViagemComponent aplicarDecorators(Viagem viagem) {
        ViagemComponent viagemComponent = new ViagemBase(viagem);

        if (viagem.isPrimeiraClasse()) {
            viagemComponent = new PrimeiraClasseDecorator(viagemComponent);
        }

        if (viagem.isAssentoJanela()) {
            viagemComponent = new AssentoJanelaDecorator(viagemComponent);
        }

        if (viagem.isGuiaTuristico()) {
            viagemComponent = new GuiaTuristicoDecorator(viagemComponent);
        }

        return viagemComponent;
    }

    // Method to calculate the actual price with all options
    public double calcularPrecoFinal(Viagem viagem) {
        return aplicarDecorators(viagem).getPreco();
    }

    public void atualizarViagem(Integer id, String tipoViagem, String origem, String destino, double preco, LocalDate dataPartida, LocalDate dataRetorno, String[] tipos, Integer clienteId) {
        Viagem viagem = findById(id);
        viagem.setOrigem(origem);
        viagem.setDestino(destino);
        viagem.setPreco(preco);
        viagem.setDataPartida(dataPartida);
        viagem.setDataRetorno(dataRetorno);

        if (clienteId != null) {
            Cliente cliente = clienteService.findById(clienteId);
            viagem.setCliente(cliente);
        }

        // Clear previous options
        viagem.setPrimeiraClasse(false);
        viagem.setAssentoJanela(false);
        viagem.setGuiaTuristico(false);

        // Apply new options
        if (tipos != null && tipos.length > 0) {
            for (String tipo : tipos) {
                switch (tipo) {
                    case "primeira_classe":
                        viagem.setPrimeiraClasse(true);
                        break;
                    case "assento_janela":
                        viagem.setAssentoJanela(true);
                        break;
                    case "guia_turistico":
                        viagem.setGuiaTuristico(true);
                        break;
                }
            }
        }

        // Recalculate price with decorators
        horas.crazytrips.model.ViagemComponent viagemComponent = aplicarDecorators(viagem);
        viagem.setPreco(viagemComponent.getPreco());

        save(viagem);
    }
}