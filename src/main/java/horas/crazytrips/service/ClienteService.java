package horas.crazytrips.service;

import horas.crazytrips.model.Cliente;
import horas.crazytrips.model.ClienteBuilder;
import horas.crazytrips.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Basic CRUD operations
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }

    // Business logic method using builder pattern
    public Cliente criarNovoCliente(String nome, String cpf, LocalDate dataNascimento,
                                    String telefone, String cep) {
        Cliente cliente = new ClienteBuilder()
                .setNome(nome)
                .setCpf(cpf)
                .setDataNascimento(dataNascimento)
                .setTelefone(telefone)
                .setCep(cep)
                .build();

        return clienteRepository.save(cliente);
    }

    // Method to update an existing client
    public Cliente atualizarCliente(Integer id, Cliente clienteAtualizado) {
        Cliente clienteExistente = findById(id);

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setCep(clienteAtualizado.getCep());

        return clienteRepository.save(clienteExistente);
    }
}