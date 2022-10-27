package com.betswap.market.infrastruture.utils.helper.mail;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "com.mail")
public class MailHelper {

    @Autowired
    JavaMailSenderImpl mailSender;

    private String from;
    private String title;
    private String identity;
    private static final int port = 465;
    @Value(value = "${com.mail.updatePasswordUrl}")
    private String updatePasswordUrl;

    @PostConstruct
    public void init(){
        mailSender.setPort(port);
        mailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");//开启认证
        properties.put("mail.smtp.ssl.enable", true);
        properties.setProperty("mail.debug", "true");//启用调试
        properties.setProperty("mail.smtp.timeout", "25000");//设置链接超时
        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));//设置ssl端口
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(properties);

    }

    public boolean sendMail(String to, String emailServiceCode){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("【" + identity + "】 " + title);
            message.setText("【" + identity + "】 " +" 本次请求的验证码为：" + emailServiceCode);
            message.setFrom(from);
            message.setTo(to);
            mailSender.send(message);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean UpdatePassWordMail(String userEmail, String uuid){
        try {
            //尝试发送html邮件，被腾讯服务器拦截不给发
//            String shtml="<label for=\"password\">新密码<span class=\"color-red\">*</span></label>\n" +
//                    "          <input id=\"password\" type=\"text\" class=\"form-control\">";
//
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//            Multipart mainPart = new MimeMultipart();
//            // 创建一个包含HTML内容的MimeBodyPart
//            BodyPart html = new MimeBodyPart();
//            // 设置HTML内容
//            html.setContent(shtml, "text/html; charset=utf-8");
//            mainPart.addBodyPart(html);
//            mimeMessage.setContent(mainPart);
//            mimeMessage.setFrom(from);
//            mimeMessage.setRecipients(Message.RecipientType.CC,userEmail);
//            mimeMessage.setSubject("subject");
//            mailSender.send(mimeMessage);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("【" + identity + "】 " + "重置密码链接");
            message.setText("请不要把该链接转发给任何人！点击:"+updatePasswordUrl+uuid);
            message.setFrom(from);
            message.setTo(userEmail);
            mailSender.send(message);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }




}
