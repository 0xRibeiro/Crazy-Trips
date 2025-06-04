package horas.crazytrips.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("nacional")
@Setter
@Getter
public class ViagemNacional extends Viagem {

    public ViagemNacional() {
        super();
        setTipo("nacional");
    }

}
