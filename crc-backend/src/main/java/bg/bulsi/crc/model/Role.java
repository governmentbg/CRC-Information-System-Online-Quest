package bg.bulsi.crc.model;

import bg.bulsi.crc.model.common.PersistableEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true, callSuper = true)
@ToString
public class Role extends PersistableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    @Getter
    @Setter
    private RoleName name;

    public Role() {}

    public Role(RoleName name) {
        this.name = name;
    }

}