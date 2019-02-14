package bg.bulsi.crc.payload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@EqualsAndHashCode
@ToString
public class UserProfile {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Instant joinedAt;
    @Getter
    @Setter
    private Long pollCount;
    @Getter
    @Setter
    private Long voteCount;

    public UserProfile(Long id, String username, String name, Instant joinedAt, Long pollCount, Long voteCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.pollCount = pollCount;
        this.voteCount = voteCount;
    }

}
