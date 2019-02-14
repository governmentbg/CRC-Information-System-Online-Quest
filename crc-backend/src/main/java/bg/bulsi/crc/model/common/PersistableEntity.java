package bg.bulsi.crc.model.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
@EqualsAndHashCode(exclude ={"version"} ,doNotUseGetters = true)
@ToString
public abstract class PersistableEntity<T extends Serializable> implements Persistable<T>, Serializable {

    private static final long serialVersionUID = -5831990268027272661L;

    @Getter
    @Setter
    @Version
    private Integer version;

    @Override
    public boolean isNew() {
        return getId() == null ||
                ((Integer)0).equals(version);
    }
}
