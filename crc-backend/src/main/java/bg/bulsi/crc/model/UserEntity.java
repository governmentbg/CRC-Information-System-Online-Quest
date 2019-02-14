package bg.bulsi.crc.model;


import bg.bulsi.crc.model.profile.CompanyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "UserEntity")
@DiscriminatorValue("1")
public class UserEntity extends UserBaseEntity {

    private static final long serialVersionUID = 1060302774326713221L;

    public UserEntity() {
    }

    public UserEntity(String firstName, String middleName, String lastName, String username, String email, String password) {
        super(firstName, middleName, lastName, username, email, password);
    }

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id")
    @Getter
    @Setter
    private Set<CompanyEntity> companyToServe = new HashSet<>();

}