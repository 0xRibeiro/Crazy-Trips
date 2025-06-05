package horas.crazytrips.service;

import horas.crazytrips.model.*;
import horas.crazytrips.repository.ClienteRepository;
import horas.crazytrips.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public ViagemService(ViagemRepository viagemRepository, ClienteRepository clienteRepository) {
        this.viagemRepository = viagemRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Viagem> findAll() {
        return viagemRepository.findAll();
    }

    public Viagem findById(Integer id) {
        return viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada com id: " + id));
    }

    public void save(Viagem viagem) {
        viagemRepository.save(viagem);
    }

    public void deleteById(Integer id) {
        viagemRepository.deleteById(id);
    }

    public Viagem criarNovaViagem(String tipo, String origem, String destino, double preco,
                                  LocalDate dataPartida, LocalDate dataRetorno,
                                  String transporte, Integer clienteId) {

        Viagem viagem = ViagemFactory.criarViagem(tipo);
        viagem.setOrigem(origem);
        viagem.setDestino(destino);
        viagem.setPreco(preco);
        viagem.setDataPartida(dataPartida);
        viagem.setDataRetorno(dataRetorno);

        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + clienteId));
            viagem.setCliente(cliente);
        }

        return viagemRepository.save(viagem);
    }

    public void atualizarViagem(Integer id, Viagem viagemAtualizada) {
        Viagem viagemExistente = findById(id);

        viagemExistente.setOrigem(viagemAtualizada.getOrigem());
        viagemExistente.setDestino(viagemAtualizada.getDestino());
        viagemExistente.setPreco(viagemAtualizada.getPreco());
        viagemExistente.setDataPartida(viagemAtualizada.getDataPartida());
        viagemExistente.setDataRetorno(viagemAtualizada.getDataRetorno());

        viagemRepository.save(viagemExistente);
    }
}