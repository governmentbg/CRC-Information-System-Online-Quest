package bg.bulsi.crc.model.audit;

import bg.bulsi.crc.model.common.PersistableEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@EqualsAndHashCode(callSuper = true, exclude = {"createdAt", "updatedAt"})
@ToString(callSuper = true)
public abstract class DateAudit<T extends Serializable> extends PersistableEntity<T> implements Serializable {

    private static final long serialVersionUID = -7913812977959009969L;

    @CreatedDate
    @Getter
    @Setter
    private Instant createdAt;

    @LastModifiedDate
    @Getter
    @Setter
    private Instant updatedAt;

}
