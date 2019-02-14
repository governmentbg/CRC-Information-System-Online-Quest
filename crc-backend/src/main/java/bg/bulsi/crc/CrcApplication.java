package bg.bulsi.crc;

import bg.bulsi.crc.config.properties.ApplicationProperties;
import bg.bulsi.crc.config.properties.JwtProperties;
import bg.bulsi.crc.config.properties.SamlSpProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({
        SamlSpProperties.class,
        JwtProperties.class,
        ApplicationProperties.class
})
public class CrcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CrcApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
