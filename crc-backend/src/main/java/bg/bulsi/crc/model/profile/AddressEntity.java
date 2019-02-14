package bg.bulsi.crc.model.profile;

import bg.bulsi.crc.model.audit.UserDateAudit;
import bg.bulsi.crc.model.ekatte.EkatteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "address", indexes = {
        @Index(name = "IX_ekatte", columnList = "ekatte")
})
@EqualsAndHashCode(callSuper = true, exclude = {"id"})
public class AddressEntity extends UserDateAudit<Long> {

    private static final long serialVersionUID = -8808681757366122024L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "ekatte", foreignKey = @ForeignKey(name = "fk_ekatte"))
    @Getter
    @Setter
    private EkatteEntity ekatte;

    @Column(name = "address")
    @Getter
    @Setter
    private String addressDescription;

    @Column(name = "postal_code")
    @Getter
    @Setter
    private String postalCode;

}
