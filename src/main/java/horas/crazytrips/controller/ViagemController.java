package horas.crazytrips.controller;

import horas.crazytrips.model.Cliente;
import horas.crazytrips.model.Viagem;
import horas.crazytrips.service.ClienteService;
import horas.crazytrips.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

        viagemService.criarNovaViagem(
                tipoViagem, origem, destino, preco,
                dataPartida, dataChegada, tipos, clienteId
        );

        return "redirect:/viagem/listar";
    }

    @GetMapping("/viagem/listar")
    public ModelAndView listarViagens() {
        ModelAndView modelAndView = new ModelAndView("listar-viagens");
        modelAndView.addObject("viagens", viagemService.findAll());
        return modelAndView;
    }

    @GetMapping("/viagem/{id}")
    public ModelAndView mostrarViagem(@PathVariable Integer id) {
        try {
            ModelAndView modelAndView = new ModelAndView("viagem-detalhe");
            modelAndView.addObject("viagem", viagemService.findById(id));
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/viagem/editar/{id}")
    public ModelAndView editarViagem(@PathVariable Integer id) {
        try {
            ModelAndView modelAndView = new ModelAndView("viagem-editar");
            modelAndView.addObject("viagem", viagemService.findById(id));
            modelAndView.addObject("clientes", clienteService.findAll());
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/viagem/listar");
        }
    }

    @PostMapping("/viagem/atualizar/{id}")
    public String atualizarViagem(
            @PathVariable Integer id,
            @RequestParam("tipoViagem") String tipoViagem,
            @RequestParam("origem") String origem,
            @RequestParam("destino") String destino,
            @RequestParam("preco") double preco,
            @RequestParam("dataPartida") LocalDate dataPartida,
            @RequestParam("dataRetorno") LocalDate dataRetorno,
            @RequestParam("clienteId") Integer clienteId,
            @RequestParam(value = "tipo", required = false) String[] tipos) {

        try {
            viagemService.atualizarViagem(
                    id, tipoViagem, origem, destino, preco,
                    dataPartida, dataRetorno, tipos, clienteId
            );
            return "redirect:/viagem/" + id;
        } catch (Exception e) {
            return "redirect:/viagem/listar";
        }
    }

    @PostMapping("/viagem/deletar/{id}")
    public String deletarViagem(@PathVariable Integer id) {
        try {
            viagemService.deleteById(id);
            return "redirect:/viagem/listar";
        } catch (Exception e) {
            return "redirect:/viagem/listar";
        }
    }
}