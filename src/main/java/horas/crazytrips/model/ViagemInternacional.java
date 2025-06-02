package horas.crazytrips.model;

public class ViagemInternacional extends Viagem {
    private String paisOrigem;
    private String paisDestino;

    public ViagemInternacional() {
        super();
        setTipo("internacional");
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }
}
