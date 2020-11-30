package website.lhc.heron.service;

import javax.mail.MessagingException;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/11/22 下午 02:00
 */
public interface MailService {

    /**
     * 发送文件邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendTextMail(String to, String subject, String content);

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;

}
