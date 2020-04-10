package io.devzona.springboot.emailbatch.batch;

import io.devzona.springboot.emailbatch.entity.Employee;
import io.devzona.springboot.emailbatch.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.*;

@Slf4j
@Component
public class EmailWritter implements ItemWriter<Employee> {

    @Autowired
    private EmailService emailService;

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        log.info("All Employees is {}", items.toString());
        items.forEach(System.out::println);
        items.forEach(employee -> {
            //emailService.sendMail("rohit.kumar6@magicbricks.com", "Test", "Hello test");
            try {
                String template = "email-template";
                String to = employee.getEmail();
                String subject = "Thymeleaf Test";
                List<String> fileToAttach = Arrays.asList("/home/rohitkumar/Downloads/0.png", "/home/rohitkumar/Documents/Study-Docs/Install-Java.txt");
                Map<String, String> inlineResource = new HashMap<>();
                /*inlineResource.put("whatsap", "/home/rohitkumar/Downloads/whatsapp.png");
                inlineResource.put("email", "/home/rohitkumar/Downloads/email.jpg");
                inlineResource.put("fb", "/home/rohitkumar/Downloads/facebook_logos.png");
                inlineResource.put("twitter", "/home/rohitkumar/Downloads/twitter_PNG.png");
                inlineResource.put("youtube", "/home/rohitkumar/Downloads/youtube_logo.png");
                inlineResource.put("linkedin", "/home/rohitkumar/Downloads/linkedIn_PNG.png");*/
                inlineResource.put("logo", "/home/rohitkumar/Downloads/0.png");
                Map<String, Object> mailVar = new HashMap<>();
                mailVar.put("fullName", employee.getFirstName() + " " + employee.getLastName());
                emailService.sendMailWithTemplateAndInlineResources(template, to, subject, fileToAttach,
                        inlineResource, true, mailVar);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
