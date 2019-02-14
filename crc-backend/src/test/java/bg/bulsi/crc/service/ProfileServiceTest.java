package bg.bulsi.crc.service;

import bg.bulsi.crc.api.dto.User;
import bg.bulsi.crc.api.dto.UserUsernamePassData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProfileServiceTest {


    @Autowired
    private ProfileService profileService;

    @Test
    public void test_create_user() {

        User user = new User();

        //user.id(1001L);
        user.egn("1234567890").firstName("bla").lastName("bla").email("aa9090@aa.com");
        UserUsernamePassData pass = new UserUsernamePassData();
        pass.password("123").username("aa9090");
        user.setUsernamePassData(pass);

        profileService.createUser(user);


    }
}
