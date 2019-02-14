package bg.bulsi.crc.service;


import bg.bulsi.crc.service.email.AsyncEmailService;
import bg.bulsi.crc.service.email.MailContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private AsyncEmailService emailService;

    @Test
    public void send_mail() {


        final MailContent content = new MailContent()
                .to("aa@aa.com")
                .from("michael.spasov@bul-si.bg")
                .subject("Account created");

        content.template()
                .templateName("email1-template")
                .property("name", "gogo");

        emailService.sendMail(content);

    }
}
