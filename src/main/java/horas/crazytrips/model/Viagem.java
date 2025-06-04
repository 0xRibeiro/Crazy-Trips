package horas.crazytrips.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "viagem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Changed from JOINED to SINGLE_TABLE
@DiscriminatorColumn(name = "tipo")
@Getter
@Setter
public abstract class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String origem;
    private String destino;
    private double preco;

    @Column(name = "data_partida")
    private LocalDate dataPartida;

    @Column(name = "data_retorno")
    private LocalDate dataRetorno;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

    @Column(name = "primeira_classe")
    private boolean primeiraClasse;

    @Column(name = "assento_janela")
    private boolean assentoJanela;

    @Column(name = "guia_turistico")
    private boolean guiaTuristico;
}