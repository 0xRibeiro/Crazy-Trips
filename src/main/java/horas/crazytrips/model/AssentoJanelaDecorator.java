package horas.crazytrips.model;

import horas.crazytrips.model.ViagemComponent;
import horas.crazytrips.model.ViagemDecorator;

public class AssentoJanelaDecorator extends ViagemDecorator {
    public AssentoJanelaDecorator(ViagemComponent viagemComponent) {
        super(viagemComponent);
    }

    @Override
    public double getPreco() {
        return super.getPreco() * PRICE_INCREASE_FACTOR;
    }
}