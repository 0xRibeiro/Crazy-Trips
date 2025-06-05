package horas.crazytrips.service;

import horas.crazytrips.model.Cliente;
import lombok.Getter;

public class CadastrarClienteCommand implements Command {

    private final ClienteService clienteService;
    private final Cliente cliente;
    @Getter
    private Cliente clienteSalvo;

    public CadastrarClienteCommand(ClienteService clienteService, Cliente cliente) {
        this.clienteService = clienteService;
        this.cliente = cliente;
    }

    @Override
    public void execute() {
        clienteSalvo = clienteService.save(cliente);
    }

    @Override
    public void undo() {
        if (clienteSalvo != null) {
            clienteService.deleteById(clienteSalvo.getId());
            clienteSalvo = null;
        }
    }
}