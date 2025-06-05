package horas.crazytrips.model;

import horas.crazytrips.model.ViagemComponent;

public abstract class ViagemDecorator implements ViagemComponent {
    protected final ViagemComponent viagemComponent;
    protected final double PRICE_INCREASE_FACTOR = 1.1; // 10% increase

    public ViagemDecorator(ViagemComponent viagemComponent) {
        this.viagemComponent = viagemComponent;
    }

    @Override
    public double getPreco() {
        return viagemComponent.getPreco();
    }
}