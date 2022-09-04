package com.yemek.sirasi.Controller;

import com.yemek.sirasi.Mail.EmailDetails;
import com.yemek.sirasi.Mail.EmailService;
import com.yemek.sirasi.Service.EmployeeService;
import com.yemek.sirasi.Service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;
    private final EmployeeService employeeService;
    private final FoodService foodService;

    @GetMapping("/mail")
    public String sendAllMail(){
        EmailDetails details=new EmailDetails();
        details.setRecipient(employeeService.emails());
        details.setMsgBody(foodService.foodNames());
        details.setSubject("Günlük Yemek");

        emailService.sendSimpleEmail(details);

        return "redirect:/listele";
    }

    /*
     private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
     */
}
