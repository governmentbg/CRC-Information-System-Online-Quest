package bg.bulsi.crc.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@EqualsAndHashCode(callSuper = true, exclude = {"createdBy", "updatedBy"})
@ToString(callSuper = true)
public abstract class UserDateAudit<T extends Serializable> extends DateAudit<T> {

    private static final long serialVersionUID = -7131452796579992589L;

    @CreatedBy
    @Getter
    @Setter
    private Long createdBy;

    @LastModifiedBy
    @Getter
    @Setter
    private Long updatedBy;

}
