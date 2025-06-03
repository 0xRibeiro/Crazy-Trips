package horas.crazytrips.model;

public class ViagemNacional extends Viagem {
    private String estadoOrigem;
    private String estadoDestino;

    public ViagemNacional() {
        super();
        setTipo("nacional");
    }

    public String getEstadoOrigem() {
        return estadoOrigem;
    }

    public void setEstadoOrigem(String estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }
}
