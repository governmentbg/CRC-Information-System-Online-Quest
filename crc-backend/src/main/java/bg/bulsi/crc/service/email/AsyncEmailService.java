package bg.bulsi.crc.service.email;

import java.util.concurrent.Future;

public interface AsyncEmailService {

    Future<String> sendMail(MailContent mail);

    Future<String> sendMailWithImages(MailContent mail);
}
