package bg.bulsi.crc.config.properties;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ConfigurationProperties("app.crc")
@EqualsAndHashCode
@ToString
@Validated
public class ApplicationProperties implements InitializingBean, Serializable {

    private static final long serialVersionUID = 6065671644271460396L;

    @Getter
    @Setter
    @Valid
    private EmailProperties email = new EmailProperties();

    @Getter
    @Setter
    @Valid
    private Questionnaire questionnaire = new Questionnaire();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public class EmailProperties implements Serializable {

        private static final long serialVersionUID = -7356807572652596428L;

        @Getter
        @Setter
        @Email
        private String from;

        @Getter
        @Setter
        private String body;

        @Getter
        @Setter
        private String subject;

    }

    public class Questionnaire implements Serializable {

        private static final long serialVersionUID = -21984576117251243L;

        @Getter
        @Setter
        @NotBlank
        private String zesTemplate;

        @Getter
        @Setter
        @NotBlank
        private String zpuTemplate;
    }
}
