package bg.bulsi.crc.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@ConfigurationProperties("jwt.config")
@EqualsAndHashCode
@ToString
@Validated
public class JwtProperties implements InitializingBean, Serializable {

    private static final long serialVersionUID = 6535840195711301365L;

    @Getter
    @Setter
    @NotBlank
    private String secret;

    @Getter
    @Setter
    @Positive
    @Min(value = 200000)
    private int expirationInMs;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
