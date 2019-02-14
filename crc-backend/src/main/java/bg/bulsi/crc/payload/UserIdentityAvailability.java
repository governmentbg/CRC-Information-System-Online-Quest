package bg.bulsi.crc.payload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class UserIdentityAvailability {
    @Getter
    @Setter
    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

}
