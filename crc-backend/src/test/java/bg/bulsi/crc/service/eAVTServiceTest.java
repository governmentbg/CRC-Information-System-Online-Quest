package bg.bulsi.crc.service;

import bg.bulsi.eauth.exception.EAuthProcessException;
import bg.bulsi.eauth.model.AuthData;
import bg.bulsi.eauth.model.UserData;
import bg.bulsi.eauth.saml.authnrequest.dom.AuthRqParams;
import bg.bulsi.eauth.saml.response.AuthenticationUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("eAVT Service tests.")
//@DataJpaTest
class eAVTServiceTest {

    @Autowired
    eAVTService eAVTService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test()
    @DisplayName("Test the eAVT SAML request generator.")
    void generateSamlRequest() throws EAuthProcessException {
        AuthRqParams a = eAVTService.generateSamlRequest();

        assertThat(a.geteAuthenticatorUrl()).isEqualTo("http://localhost:8080/eforms-portal/eauth/eauthRsTest.xhtml");
    }

    @Test
    @DisplayName("Test aAVT SAML request.")
    void parseSamlResponse() {
        AuthData authData = eAVTService.parseSamlResponse(SamlResponse.samlRsBase64, SamlResponse.relayStateBase64);

        assertThat(authData.getStatus()).isEqualByComparingTo( AuthenticationUseCase.AUTHENTICATED_SUCCESSFULLY);
        assertThat(authData.getUserData()).extracting(UserData::getEgn).isEqualTo(SamlResponse.pid);
        assertThat(authData.getUserData()).extracting(UserData::getEmail).isEqualTo(SamlResponse.email);
    }
}