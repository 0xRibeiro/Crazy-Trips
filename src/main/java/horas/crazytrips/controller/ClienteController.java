package horas.crazytrips.controller;

import horas.crazytrips.model.Cliente;
import horas.crazytrips.service.CadastrarClienteCommand;
import horas.crazytrips.service.ClienteService;
import horas.crazytrips.service.CommandInvoker;
import horas.crazytrips.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {

    private final ViagemService viagemService;
    private final ClienteService clienteService;
    private final CommandInvoker commandInvoker;

    @Autowired
    public ClienteController(ViagemService viagemService, ClienteService clienteService, CommandInvoker commandInvoker) {
        this.viagemService = viagemService;
        this.clienteService = clienteService;
        this.commandInvoker = commandInvoker;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            CadastrarClienteCommand command = new CadastrarClienteCommand(clienteService, cliente);
            commandInvoker.executeCommand(command);
            return ResponseEntity.ok(command.getClienteSalvo());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @PostMapping("/desfazer-ultimo-cadastro")
    public ResponseEntity<?> desfazerUltimoComando() {
        try {
            if (commandInvoker.hasCommands()) {
                commandInvoker.undoLastCommand();
                return ResponseEntity.ok("Último cadastro desfeito com sucesso");
            } else {
                return ResponseEntity.badRequest().body("Não há operações para desfazer");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao desfazer operação: " + e.getMessage());
        }
    }

    @GetMapping("/api/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }


    @GetMapping("/cliente/listar")
    public ModelAndView listarClientes() {
        ModelAndView modelAndView = new ModelAndView("listar-clientes");
        modelAndView.addObject("clientes", clienteService.findAll());
        return modelAndView;
    }

    @GetMapping("/cliente/{id}")
    public ModelAndView mostrarCliente(@PathVariable Integer id) {
        try {
            ModelAndView modelAndView = new ModelAndView("cliente-detalhe");
            modelAndView.addObject("cliente", clienteService.findById(id));
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Integer id) {
        try {
            ModelAndView modelAndView = new ModelAndView("cliente-editar");
            modelAndView.addObject("cliente", clienteService.findById(id));
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/cliente/listar");
        }
    }

    @PostMapping("/cliente/atualizar/{id}")
    public String atualizarCliente(@PathVariable Integer id, @ModelAttribute Cliente cliente) {
        try {
            clienteService.atualizarCliente(id, cliente);
            return "redirect:/cliente/" + id;
        } catch (Exception e) {
            return "redirect:/cliente/listar";
        }
    }

    @PostMapping("/cliente/deletar/{id}")
    public String deletarCliente(@PathVariable Integer id) {
        try {
            clienteService.deleteById(id);
            return "redirect:/cliente/listar";
        } catch (Exception e) {
            return "redirect:/cliente/listar";
        }
    }
}