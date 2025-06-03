package horas.crazytrips.model;

import java.time.LocalDate;

public class ClienteBuilder {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String cep;

    public ClienteBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public ClienteBuilder setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public ClienteBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public Cliente build() {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setDataNascimento(dataNascimento);
        cliente.setTelefone(telefone);
        cliente.setCep(cep);
        return cliente;
    }
}
