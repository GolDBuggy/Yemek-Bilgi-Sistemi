package com.yemek.sirasi.Mail;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.VerificationToken;
import com.yemek.sirasi.Service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final VerificationTokenService service;
    private final TemplateEngine engine;
    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String mailSender;


    private Logger logger=Logger.getLogger(EmailService.class.getName());

    public void sendHtmlMail(Employee employee) throws MessagingException {
        VerificationToken verificationToken= service.findByEmployee(employee);
        if (verificationToken != null){
            String token=verificationToken.getToken();
            Context context=new Context();
            context.setVariable("title","Verify your email address!");
            context.setVariable("link","http://localhost:8080/activation?token="+token);

            String body=engine.process("verification",context);

            MimeMessage message= sender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setTo(employee.getEmail());
            helper.setSubject("Email address verification!");
            helper.setText(body,true);
            sender.send(message);
        }
    }


    public String sendSimpleEmail(EmailDetails emailDetails){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            String[] mailList=emailDetails.getRecipient().toArray(String[]::new);

            message.setFrom(mailSender);
            message.setTo(mailList);
            message.setText(emailDetails.getMsgBody() +"\n link= 10.200.20.88:8080/listele");
            message.setSubject(emailDetails.getSubject());

            sender.send(message);

            return "Mail başarıyla gönderildi!";
        }catch (Exception e){
            logger.info(e.getMessage());
            return e.getMessage();
        }
    }
}
