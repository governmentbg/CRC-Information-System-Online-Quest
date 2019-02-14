package bg.bulsi.crc.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ConfigurationProperties("eavt.sp")
@EqualsAndHashCode
@ToString
@Validated
public class SamlSpProperties implements InitializingBean, Serializable {

    private static final long serialVersionUID = -7757566752649092716L;

    @Getter
    @Setter
    @Valid
    private Service service = new Service();
    @Getter
    @Setter
    @Valid
    private Saml saml = new Saml();
    @Getter
    @Setter
    @Valid
    private DigitalSignature digitalSignature = new DigitalSignature();
    @Getter
    @Setter
    @NotBlank
    private String relayState;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @EqualsAndHashCode
    @ToString
    @Validated
    public class Service implements Serializable {

        private static final long serialVersionUID = -8931426441218708001L;

        @Getter
        @Setter
        @NotBlank
        private String serviceName;
        @Getter
        @Setter
        @NotBlank
        private String serviceOid;
        @Getter
        @Setter
        @NotBlank
        private String serviceUrl;
        @Getter
        @Setter
        @NotBlank
        private String serviceProviderName;
        @Getter
        @Setter
        @NotBlank
        private String serviceProviderOid;
    }

    @EqualsAndHashCode
    @ToString
    @Validated
    public class Saml implements Serializable {
        private static final long serialVersionUID = 2797991567216859437L;
        @Getter
        @Setter
        @NotBlank
        private String eavtUrl;
        @Getter
        @Setter
        @NotBlank
        private String portalUrl;
        @Getter
        @Setter
        @NotBlank
        private String portalOid;
        @Getter
        @Setter
        private String idPrefix;
        @Getter
        @Setter
        private String makeStsTokenValidation;
    }

    @EqualsAndHashCode
    @ToString
    @Validated
    public class DigitalSignature implements Serializable {
        private static final long serialVersionUID = -5545645398689667703L;
        @Getter
        @Setter
        @NotBlank
        private String keystoreType;
        @Getter
        @Setter
        @NotBlank
        private String keystoreFileName;
        @Getter
        @Setter
        @NotBlank
        private String keystorePass;
        @Getter
        @Setter
        @NotBlank
        private String privateAlias;
        @Getter
        @Setter
        @NotBlank
        private String privateKeyPass;
        @Getter
        @Setter
        @NotBlank
        private String eavtCertificateName;
    }
}
