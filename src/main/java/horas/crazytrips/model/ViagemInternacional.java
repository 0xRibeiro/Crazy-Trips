package horas.crazytrips.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("internacional")
public class ViagemInternacional extends Viagem {

    public ViagemInternacional() {
        super();
        setTipo("internacional");
    }

}
