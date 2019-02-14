package bg.bulsi.crc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "v_v2_1")
@Immutable
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class PostNomenclature implements Serializable {

    private static final long serialVersionUID = 2137522259712440145L;

    @Getter
    @Setter
    @Id
    @Column(name = "code")
    private String code;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Column(name = "key")
    private String key;

  /*  @Getter
    @Setter
    @Column(name = "sum_f")
    private Boolean sumF;

    @Getter
    @Setter
    @Column(name = "discount_f")
    private Boolean discountF;*/

}