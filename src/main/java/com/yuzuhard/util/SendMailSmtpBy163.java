//package com.yuzuhard.util;
//
//import java.util.Properties;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import com.sun.mail.util.MailSSLSocketFactory;
//
//public class SendMailSmtpBy163 {
////    private static Logger log = Logger.getLogger(SendTemplateSMS.class);
//
//    public static void sendHtmlMail() {
//        try {
//            String from = "yuzu-hard";//发件人昵称展示   *
//            String[] to = {"2026085674@qq.com"};//接收邮箱
//            String subject = "邮件主题";//邮件主题   *
//            String text = "邮件内容";
//            String host = "smtphz.qiye.163.com";//163企业邮箱smtp   *
//            String username = "yuzu-hard@outlook.com";//企业邮箱   *
//            String password = "TdphKnTNGTuEUUf8";//企业邮箱密码   *
//
//            //设置服务器验证信息
//            Properties prop = new Properties();
//            prop.setProperty("mail.smtp.auth", "true");
//            prop.setProperty("mail.smtp.timeout", "994"); // 加密端口(ssl)  可通过 https://qiye.163.com/help/client-profile.html 进行查询
//
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();// SSL加密
//            sf.setTrustAllHosts(true); // 设置信任所有的主机
//            prop.put("mail.smtp.ssl.enable", "true");
//            prop.put("mail.smtp.ssl.socketFactory", sf);
//
//            //设置邮件内容
//            JavaMailSenderImpl javaMailSend = new JavaMailSenderImpl();
//            MimeMessage message = javaMailSend.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
//            String nick = MimeUtility.encodeText(from);//设置昵称
//            messageHelper.setFrom(new InternetAddress(nick + " <" + username + ">"));// 邮件发送者
//            messageHelper.setTo(to);
//            messageHelper.setSubject(subject);
//            messageHelper.setText(text, true);
//
//            //设置邮件服务器登录信息
//            javaMailSend.setHost(host);
//            javaMailSend.setUsername(username);
//            javaMailSend.setPassword(password);
//            javaMailSend.setJavaMailProperties(prop);
////            log.info("maillText：" + text);
//            javaMailSend.send(message);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
////            log.error(e);
//            e.printStackTrace();
//        }
//    }
//
//}