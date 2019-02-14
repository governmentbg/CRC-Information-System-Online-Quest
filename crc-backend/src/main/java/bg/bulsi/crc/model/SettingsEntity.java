package bg.bulsi.crc.model;


import bg.bulsi.crc.model.common.PersistableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class SettingsEntity extends PersistableEntity<String> {

    private static final long serialVersionUID = -4765870619976438548L;

    @Id
    @Getter
    @Setter
    @Column(name = "key", length = 20, nullable = false)
    private String id;

    @Getter
    @Setter
    @Column(name = "value", length = 128, nullable = false)
    private String value;

}
