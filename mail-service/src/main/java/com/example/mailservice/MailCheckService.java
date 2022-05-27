package com.example.mailservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailCheckService {

    private final JavaMailSender mailSender;

//    private final MailCheckHistoryRepository mailRepository;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * Метод, который отправляет на почту ссылку с подтверждением почты.
     */
    @RabbitListener(queues = "serverQueue")
    public void sendMailConfirm(MailConfirmRequest confirmRequest) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(confirmRequest.getEmail(), true);
            helper.setTo(confirmRequest.getTo());
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@mail.ru");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
