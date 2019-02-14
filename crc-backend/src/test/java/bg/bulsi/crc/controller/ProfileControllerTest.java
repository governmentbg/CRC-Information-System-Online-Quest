package bg.bulsi.crc.controller;


import bg.bulsi.crc.IntegrationTestUtil;
import bg.bulsi.crc.api.dto.Company;
import bg.bulsi.crc.api.dto.LoginRequest;
import bg.bulsi.crc.api.dto.User;
import bg.bulsi.crc.model.Role;
import bg.bulsi.crc.model.RoleName;
import bg.bulsi.crc.model.UserEntity;
import bg.bulsi.crc.security.CustomUserDetailsService;
import bg.bulsi.crc.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Disabled
class ProfileControllerTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    private String accessToken;
    private UserDetails userDetails;

    /*@BeforeAll
    static void init(){

    }*/

    @BeforeEach
    void init_security_context() throws Exception {

        LoginRequest loginRequest = new LoginRequest().usernameOrEmail("michael").password("123");
        UserEntity user = createUser();
        userDetails = UserPrincipal.create(user);

        given(this.customUserDetailsService.loadUserByUsername(anyString())).willReturn(userDetails);
        given(this.customUserDetailsService.loadUserById(anyLong())).willReturn(userDetails);

        String result = this.mvc.perform(post("/api/auth/signin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(loginRequest))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))

                .andReturn().getResponse().getContentAsString();

        accessToken = IntegrationTestUtil.extractValueFromJsonPath("$.accessToken", result);
    }

    @Test
    @Rollback
    void create_company_test() throws Exception {

        Company payload = IntegrationTestUtil.loadJsonData("json/createUser.json", Company.class);

        //ReflectionTestUtils.setField(((UserPrincipal)userDetails), "password", passwordEncoder.encode("12399"));
        //ReflectionTestUtils.setField(((UserPrincipal)userDetails), "username", "test");

        given(this.customUserDetailsService.loadUserById(anyLong()))
                //.willCallRealMethod();
                .willReturn(userDetails);

        String result = this.mvc.perform(post("/api/profile/co")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + this.accessToken)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(payload))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @Rollback
    void update_company_test() throws Exception {

        Company payload = IntegrationTestUtil.loadJsonData("json/update_company.json", Company.class);

        //ReflectionTestUtils.setField(((UserPrincipal)userDetails), "password", passwordEncoder.encode("12399"));
        //ReflectionTestUtils.setField(((UserPrincipal)userDetails), "username", "test");

        given(this.customUserDetailsService.loadUserById(anyLong()))
                //.willCallRealMethod();
                .willReturn(userDetails);

        String result = this.mvc.perform(put("/api/profile/co")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + this.accessToken)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(payload))
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @Rollback
    void create_crc_user_test() throws Exception {
        User payload = IntegrationTestUtil.loadJsonData("json/create_user.json", User.class);

        given(this.customUserDetailsService.loadUserById(anyLong()))
                .willReturn(userDetails);

        String result = this.mvc.perform(post("/api/profile/crc/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + this.accessToken)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(payload))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @Rollback
    void update_crc_user_test() throws Exception {
        User payload = IntegrationTestUtil.loadJsonData("json/create_user.json", User.class);

        given(this.customUserDetailsService.loadUserById(anyLong()))
                .willReturn(userDetails);

        String result = this.mvc.perform(put("/api/profile/crc/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + this.accessToken)
                .content(IntegrationTestUtil.convertObjectToJsonBytes(payload))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

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
}