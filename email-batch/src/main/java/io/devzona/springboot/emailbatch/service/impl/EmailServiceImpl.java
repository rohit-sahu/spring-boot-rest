package io.devzona.springboot.emailbatch.service.impl;

import io.devzona.springboot.emailbatch.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @Override
    public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) {
        /*MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource file = new FileSystemResource(new File(fileToAttach));
        helper.addAttachment("Invoice", file);*/
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);
                FileSystemResource file = new FileSystemResource(new File(fileToAttach));
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment("logo.jpg", file);
            }
        };
        try {
            javaMailSender.send(preparator);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendMailWithInlineResources(String to, String subject, String fileToAttach) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("admin@gmail.com"));
                mimeMessage.setSubject(subject);
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
                FileSystemResource res = new FileSystemResource(new File(fileToAttach));
                helper.addInline("identifier1234", res);
            }
        };
        try {
            javaMailSender.send(preparator);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendMailWithTemplateAndInlineResources(String templateName, String to, String subject,
                                                       List<String> fileToAttach, Map<String, String> inlineResourceContent,
                                                       boolean isHtml, Map<String, Object> mailVar) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        if (fileToAttach != null && !fileToAttach.isEmpty()) {
            fileToAttach.forEach(file -> {
                //UrlResource urlResource = new FileUrlResource("file://" + file);
                FileSystemResource fileSystemResource = new FileSystemResource(new File(file));
                try {
                    helper.addAttachment("Invoice", fileSystemResource);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        if (inlineResourceContent != null && !inlineResourceContent.isEmpty()) {
            inlineResourceContent.entrySet().stream().forEach(stringStringEntry -> {
                try {
                    File file = new File(stringStringEntry.getValue());
                    //helper.addInline(stringStringEntry.getKey(), new FileSystemResource(file), Files.probeContentType(Paths.get(file.toURI())));
                    helper.addInline(stringStringEntry.getKey(), file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Context context = new Context();
        context.setVariables(mailVar);
        String html = templateEngine.process(templateName, context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, isHtml);
        javaMailSender.send(message);
    }
}
