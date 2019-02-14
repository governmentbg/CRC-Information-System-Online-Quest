package bg.bulsi.crc.model.ekatte;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "n_ekatte", schema = "common")
@EqualsAndHashCode(callSuper = false, exclude = {"population", "planningArea"})
public class EkatteEntity implements Persistable<String>, Serializable {

    private static final long serialVersionUID = 3579125705165496727L;

    @Getter
    @Setter
    @Id
    @Column(length = 5)
    private String ekatte;

    @Getter
    @Setter
    private String area;
    @Getter
    @Setter
    private String municipality;
    @Getter
    @Setter
    private String place;
    @Getter
    @Setter
    @Column(name = "area_code", length = 50)
    private String areaCode;
    @Getter
    @Setter
    @Column(name = "municipality_code", length = 50)
    private String municipalityCode;
    @Getter
    @Setter
    @Column(name = "place_type", length = 50)
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    @Getter
    @Setter
    private Integer population = 0;
    @Getter
    @Setter
    @Column(name = "planning_area", length = 50)
    private String planningArea;

    @Override
    public String getId() {
        return this.ekatte;
    }

    @Override
    public boolean isNew() {
        return StringUtils.isEmpty(this.ekatte);
    }
}
