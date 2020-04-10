package io.devzona.springboot.emailbatch.service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

public interface EmailService {

    void sendMail(String to, String subject, String body);

    void sendMailWithAttachment(String to, String subject, String body, String fileToAttach);

    void sendMailWithInlineResources(String to, String subject, String fileToAttach);

    void sendMailWithTemplateAndInlineResources(String templateName, String to, String subject,
                                                List<String> fileToAttach, Map<String, String> inlineResourceContent,
                                                boolean isHtml, Map<String, Object> mailVar) throws MessagingException;
}