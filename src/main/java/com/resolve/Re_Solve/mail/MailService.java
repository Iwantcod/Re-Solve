package com.resolve.Re_Solve.mail;

import com.resolve.Re_Solve.wrong.dto.MailFormatDto;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final String fromEmail;
    @Autowired
    public MailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void sendReminder(String toEmail, String username, List<MailFormatDto> items) throws MessagingException {
        String subject = MailTemplateBuilder.buildSubject(username);
        String html    = MailTemplateBuilder.buildHtml(username, items);

        var mime = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mime, true, "UTF-8");
        helper.setFrom(fromEmail); // spring.mail.username과 동일 권장
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(mime);
    }
}
