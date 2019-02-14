package bg.bulsi.crc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.optionfactory.hj.JsonDriver;
import net.optionfactory.hj.jackson.JacksonJsonDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JsonDriver jacksonDriver() {
        return new JacksonJsonDriver(new ObjectMapper());
    }

}
