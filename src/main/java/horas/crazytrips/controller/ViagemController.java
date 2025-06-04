package horas.crazytrips.controller;

import horas.crazytrips.model.Cliente;
import horas.crazytrips.model.Viagem;
import horas.crazytrips.service.ClienteService;
import horas.crazytrips.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ViagemController {

    private final ViagemService viagemService;
    private final ClienteService clienteService;

    @Autowired
    public ViagemController(ViagemService viagemService, ClienteService clienteService) {
        this.viagemService = viagemService;
        this.clienteService = clienteService;
    }

    @PostMapping("/viagem/cadastrar")
    public String cadastrarViagem(
            @RequestParam("tipoViagem") String tipoViagem,
            @RequestParam("origem") String origem,
            @RequestParam("destino") String destino,
            @RequestParam("preco") double preco,
            @RequestParam("dataPartida") LocalDate dataPartida,
            @RequestParam("dataChegada") LocalDate dataChegada,
            @RequestParam("clienteId") Integer clienteId,
            @RequestParam(value = "tipo", required = false) String[] tipos) {

        boolean primeiraClasse = false;
        boolean assentoJanela = false;
        boolean guiaTuristico = false;

        if (tipos != null) {
            for (String tipo : tipos) {
                if ("primeira_classe".equals(tipo)) primeiraClasse = true;
                if ("assento_janela".equals(tipo)) assentoJanela = true;
                if ("guia_turistico".equals(tipo)) guiaTuristico = true;
            }
        }

        Viagem viagem = viagemService.criarNovaViagem(
                tipoViagem, origem, destino, preco,
                dataPartida, dataChegada, null, clienteId
        );

        viagem.setPrimeiraClasse(primeiraClasse);
        viagem.setAssentoJanela(assentoJanela);
        viagem.setGuiaTuristico(guiaTuristico);

        viagemService.save(viagem);

        return "redirect:/viagem/listar";
    }

    @GetMapping("/viagem/listar")
    public ModelAndView listarViagens() {
        List<Viagem> viagens = viagemService.findAll();
        ModelAndView modelAndView = new ModelAndView("listar-viagens");
        modelAndView.addObject("viagens", viagens);
        return modelAndView;
    }

    @GetMapping("/viagem/{id}")
    public ModelAndView mostrarViagem(@PathVariable Integer id) {
        try {
            Viagem viagem = viagemService.findById(id);
            ModelAndView modelAndView = new ModelAndView("viagem-detalhe");
            modelAndView.addObject("viagem", viagem);
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            return modelAndView;
        }
    }

    @GetMapping("/viagem/editar/{id}")
    public ModelAndView editarViagem(@PathVariable Integer id) {
        try {
            Viagem viagem = viagemService.findById(id);
            List<Cliente> clientes = clienteService.findAll();

            ModelAndView modelAndView = new ModelAndView("viagem-editar");
            modelAndView.addObject("viagem", viagem);
            modelAndView.addObject("clientes", clientes);
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/viagem/listar");
        }
    }

    @PostMapping("/viagem/atualizar/{id}")
    public String atualizarViagem(
            @PathVariable Integer id,
            @RequestParam("origem") String origem,
            @RequestParam("destino") String destino,
            @RequestParam("preco") double preco,
            @RequestParam("dataPartida") LocalDate dataPartida,
            @RequestParam("dataRetorno") LocalDate dataRetorno,
            @RequestParam("clienteId") Integer clienteId,
            @RequestParam(value = "tipo", required = false) String[] tipos) {

        try {
            Viagem viagem = viagemService.findById(id);
            viagem.setOrigem(origem);
            viagem.setDestino(destino);
            viagem.setPreco(preco);
            viagem.setDataPartida(dataPartida);
            viagem.setDataRetorno(dataRetorno);


            viagem.setPrimeiraClasse(false);
            viagem.setAssentoJanela(false);
            viagem.setGuiaTuristico(false);


            if (tipos != null) {
                for (String tipo : tipos) {
                    if ("primeira_classe".equals(tipo)) viagem.setPrimeiraClasse(true);
                    if ("assento_janela".equals(tipo)) viagem.setAssentoJanela(true);
                    if ("guia_turistico".equals(tipo)) viagem.setGuiaTuristico(true);
                }
            }

            Cliente cliente = clienteService.findById(clienteId);
            viagem.setCliente(cliente);

            viagemService.atualizarViagem(id, viagem);

            return "redirect:/viagem/" + id;
        } catch (Exception e) {
            return "redirect:/viagem/listar";
        }
    }
}