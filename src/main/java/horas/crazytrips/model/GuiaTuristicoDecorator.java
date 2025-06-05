package horas.crazytrips.model;

import horas.crazytrips.model.ViagemComponent;

public class GuiaTuristicoDecorator extends ViagemDecorator {
    public GuiaTuristicoDecorator(ViagemComponent viagemComponent) {
        super(viagemComponent);
    }

    @Override
    public double getPreco() {
        return super.getPreco() * PRICE_INCREASE_FACTOR;
    }
}