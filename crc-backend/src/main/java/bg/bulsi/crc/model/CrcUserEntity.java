package bg.bulsi.crc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "CrcUserEntity")
@DiscriminatorValue("2")
public class CrcUserEntity extends UserBaseEntity {

    private static final long serialVersionUID = 8937521924642021685L;

    public CrcUserEntity() {
    }

    public CrcUserEntity(String firstName, String middleName, String lastName, String username, String email, String password) {
        super(firstName, middleName, lastName, username, email, password);
    }
}
