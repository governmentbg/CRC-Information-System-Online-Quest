package bg.bulsi.crc.mapping;


import bg.bulsi.crc.IntegrationTestUtil;
import bg.bulsi.crc.api.dto.Company;
import bg.bulsi.crc.config.ApplicationConfig;
import bg.bulsi.crc.config.ModelMapperConfig;
import bg.bulsi.crc.model.UserAuthenticationType;
import bg.bulsi.crc.model.profile.CompanyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        ApplicationConfig.class,
        ModelMapperConfig.class
})

public class ProfileMappingTest {

    @Autowired
    private ModelMapper mapper;

    //    @Test
    public void test_saml_authorization_company() throws IOException {

        Company co = IntegrationTestUtil.loadJsonData("json/company_eavt.json", Company.class);

        CompanyEntity cmp = mapper.map(co, CompanyEntity.class);

        assertThat(cmp.getAuthorizedPerson()).isNotNull();
        assertThat(cmp.getAuthorizedPerson().getAuthenticationType()).isEqualTo(UserAuthenticationType.SAML);

    }

    //  @Test
    public void test_up_authorization_company() throws IOException {

        Company co = IntegrationTestUtil.loadJsonData("json/company.json", Company.class);

        CompanyEntity cmp = mapper.map(co, CompanyEntity.class);

        assertThat(cmp.getAuthorizedPerson()).isNotNull();
        assertThat(cmp.getAuthorizedPerson().getAuthenticationType()).isEqualTo(UserAuthenticationType.STANDART);
    }
}
