package com.yuzuhard.util;
import com.yuzuhard.pojo.Comment;
import com.yuzuhard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class SendMailSmtp {

    @Autowired
    CommentService commentService;

    public  void send(Comment comment) {
        Comment parentComment = commentService.get(comment.getParent());
        if (parentComment.getMail() == null || parentComment.getMail().length() == 0){
            return;
        }


        final String username = "";  // like yourname@outlook.com
        final String password = "";   // password here

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);






        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(parentComment.getMail()));   // like inzi769@gmail.com
            message.setSubject("Yuzu-Hard消息回复提醒");
            String text = comment.getOwnerName() +
                    " 回复了你的评论（" +
                    parentComment.getText() +
                    "）：" +
                    comment.getText() +
                    "\n" +
                    "链接地址：www.yuzu-hard.xyz/detail?id="+ comment.getContent().getId() +
                    "\n\n\n" +
                    "为了更好的接收消息回复，请将此邮件标记为 非垃圾邮件"+
                    "\n" +
                    "ps:即便是标记为非垃圾邮件也有可能因为连接outlook超时而收不到 (>﹏<)";
            message.setText(text);

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
