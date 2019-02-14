package bg.bulsi.crc.service.email;


import bg.bulsi.crc.config.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncEmailServiceImpl implements AsyncEmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;
    private final ApplicationProperties applicationProperties;
    private final MailContentBuilder mailContentBuilder;

    @Autowired
    public AsyncEmailServiceImpl(JavaMailSender mailSender,
                                 ResourceLoader resourceLoader,
                                 MailContentBuilder mailContentBuilder,
                                 ApplicationProperties applicationProperties) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
        this.applicationProperties = applicationProperties;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Async
    @Override
    public Future<String> sendMailWithImages(MailContent mail) {

        log.info("Start execution of async. Sending email with file attachment");

        try {
            MimeMessage message = mailSender.createMimeMessage();

            final MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(mail.to());
            helper.setSubject(mail.subject());
            helper.setFrom(Objects.requireNonNull(applicationProperties.getEmail().getFrom()));

            helper.setText(mailContentBuilder.build(mail.template()), true);

            for (Map.Entry<String, String> attachment : mail.attachments().entrySet()) {
                helper.addInline(attachment.getKey(), resourceLoader.getResource(attachment.getValue()));
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            log.error("Exception occurs in sending email!", e);
            throw new MailParseException(e);

        } catch (MailSendException e) {
            log.error("Exception occurs in sending email!", e);
        }

        return new AsyncResult<>("Email send successfully");
    }

    @Async
    @Override
    public Future<String> sendMail(MailContent mail) {

        log.info("Start execution of async. Sending email");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom(Objects.requireNonNull((applicationProperties.getEmail().getFrom())));
            helper.setTo(mail.to());
            helper.setSubject(mail.subject());
            helper.setText(mailContentBuilder.build(mail.template()), true);

            /*
             * FileSystemResource file = new FileSystemResource(attachFile);
             * helper.addAttachment(file.getFilename(), file);
             */

            mailSender.send(message);

        } catch (MessagingException e) {
            log.error("Exception occurs in sending email!", e);
            throw new MailParseException(e);

        } catch (MailSendException e) {
            log.error("Exception occurs in sending email!", e);
        }

        return new AsyncResult<>("Email send successfully");
    }
}