package website.lhc.heron.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import website.lhc.heron.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @description: 发送邮件
 * @author: 582895699@qq.com
 * @time: 2020/11/22 下午 02:02
 */

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value(value = "${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;


    @Async
    @Override
    public void sendTextMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setSentDate(new Date());
        mailSender.send(simpleMailMessage);
    }

    @Async
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        long time = System.currentTimeMillis();
        log.info("发送HTML邮件；to:{}", to);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setSentDate(new Date());
        messageHelper.setText(content, true);
        mailSender.send(mimeMessage);
        log.info("发送成功，耗时：{}ms", System.currentTimeMillis() - time);
    }
}
