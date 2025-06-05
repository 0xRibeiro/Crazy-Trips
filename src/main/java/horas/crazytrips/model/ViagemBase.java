package horas.crazytrips.model;

import horas.crazytrips.model.Viagem;
import horas.crazytrips.model.ViagemComponent;

public class ViagemBase implements ViagemComponent {
    private final Viagem viagem;

    public ViagemBase(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public double getPreco() {
        return viagem.getPreco();
    }

    public Viagem getViagem() {
        return viagem;
    }
}