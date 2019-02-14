package bg.bulsi.crc.controller;


import bg.bulsi.crc.IntegrationTestUtil;
import bg.bulsi.crc.api.dto.LoginRequest;
import bg.bulsi.crc.config.properties.JwtProperties;
import bg.bulsi.crc.model.Role;
import bg.bulsi.crc.model.RoleName;
import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.repository.RoleRepository;
import bg.bulsi.crc.repository.UserRepository;
import bg.bulsi.crc.security.CustomUserDetailsService;
import bg.bulsi.crc.security.UserPrincipal;
import bg.bulsi.crc.service.SamlResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    RoleRepository roleRepository;
    @Autowired
    private JwtProperties jwtProperties;
 /*   private UserEntity user;
    private Role userRole;*/

    @PostConstruct
    void startUp() {

    }

    private UserEntity createUser() {
    /*
        Create test USER
     */
        UserEntity user = new UserEntity();
        user.setUsername("michael");
        user.setPassword(passwordEncoder.encode("123"));
        ReflectionTestUtils.setField(user, "id", 1024L);
        user.setActive(true);
        user.setActiveFrom(LocalDate.now());
        /*
            Create test ROLE
         */
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        ReflectionTestUtils.setField(userRole, "id", 1024L);

        user.setRoles(Collections.singleton(userRole));

        return user;
    }

    @Test
    public void test_sign_in() throws Exception {

        LoginRequest loginRequest = new LoginRequest().usernameOrEmail("michael").password("123");
        UserEntity user = createUser();

        given(this.customUserDetailsService.loadUserByUsername(anyString())).willReturn(UserPrincipal.create(user));

        String result = this.mvc.perform(post("/api/auth/signin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(loginRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))

                .andReturn().getResponse().getContentAsString();

        String accessToken = IntegrationTestUtil.extractValueFromJsonPath("$.accessToken", result);
        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperties.getSecret())
                .parseClaimsJws(accessToken)
                .getBody();

        assertThat(claims.getIssuer()).isEqualTo("STANDARD");
        assertThat(claims.getSubject()).isEqualTo("1024");
    }

    @Test
    public void test_saml_sign_in() throws Exception {

        UserEntity user = setupValidSamlUser();
        Role userRole = user.getRoles().iterator().next();

        given(this.customUserDetailsService.loadUserByUsername(anyString())).willReturn(UserPrincipal.create(user));
        given(this.roleRepository.findByName(any())).willReturn(Optional.of(userRole));
        given(this.userRepository.save(any())).willReturn(user);

        String result = this.mvc.perform(post("/api/auth/saml-signin")
                .param("samlResponse", SamlResponse.samlRsBase64)
                .param("relayState", SamlResponse.relayStateBase64)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))

                .andReturn().getResponse().getContentAsString();

        String accessToken = IntegrationTestUtil.extractValueFromJsonPath("$.accessToken", result);

        Claims claims = Jwts.parser()
                .setSigningKey(this.jwtProperties.getSecret())
                .parseClaimsJws(accessToken)
                .getBody();

        assertThat(claims.getIssuer()).isEqualTo("SAML");
        assertThat(claims.getSubject()).isEqualTo("1024");
    }

    @Test
    public void test_saml_sign_in_wrong_password() throws Exception {

        UserEntity user = setupValidSamlUser();
        user.setPassword("Wrong_Pass");
        Role userRole = user.getRoles().iterator().next();

        given(this.customUserDetailsService.loadUserByUsername(anyString())).willReturn(UserPrincipal.create(user));
        given(this.roleRepository.findByName(any())).willReturn(Optional.of(userRole));
        given(this.userRepository.save(any())).willReturn(user);

        this.mvc.perform(post("/api/auth/saml-signin")
                .param("samlResponse", SamlResponse.samlRsBase64)
                .param("relayState", SamlResponse.relayStateBase64)
        )
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

                .andReturn().getResponse().getContentAsString();


    }

    private UserEntity setupValidSamlUser() {
        UserEntity user = createUser();
        user.setPassword(passwordEncoder.encode(SamlResponse.pid));
        user.setUsername(SamlResponse.pid);
        user.setFirstName(SamlResponse.name);
        user.setEmail(SamlResponse.email);

        return user;
    }

}