package com.yemek.sirasi.Controller;

import com.yemek.sirasi.Dto.EmployeeDTO;
import com.yemek.sirasi.Dto.RegisterDTO;
import com.yemek.sirasi.Mail.EmailDetails;
import com.yemek.sirasi.Mail.EmailService;
import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.Food;
import com.yemek.sirasi.Model.VerificationToken;
import com.yemek.sirasi.Service.EmployeeService;
import com.yemek.sirasi.Service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final VerificationTokenService tokenService;

    private static Logger logger=Logger.getLogger(EmployeeController.class.getName());



    @PostMapping("/save")
    public String saveEmp(@ModelAttribute("register") RegisterDTO employee,Model model){
        service.saveEmp(employee);
        Optional<Employee> emp=service.getByEmail(employee.getEmail());
        VerificationToken verificationToken=tokenService.findByEmployee(emp.get());
        model.addAttribute("mail",employee.getEmail());

        return "verification";
    }


    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("register",new RegisterDTO());
        return "register-page";
    }



    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token,Model model){
        VerificationToken verificationToken= tokenService.findByToken(token);
        if(verificationToken==null){
            model.addAttribute("message","Your token is invalid!");
        }else{
            Employee employee=verificationToken.getEmployee();

            if (!employee.isActive()){
                employee.setActive(true);

                service.save(employee);
            }
        }

        return "redirect:/login";
    }





}
