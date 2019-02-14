package bg.bulsi.crc.model.profile;


import bg.bulsi.crc.model.NamePrefixEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode()
@ToString
public class CompanyRepresentative implements Serializable {

    private static final long serialVersionUID = 8190208063737323680L;

    @Column(name = "name_prefix", length = 20)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private NamePrefixEnum namePrefix;

    @Column(name = "first_name")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "middle_name")
    @Getter
    @Setter
    private String middleName;

    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String position;
}
