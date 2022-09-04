package com.yemek.sirasi.Service;

import com.yemek.sirasi.Dto.EmployeeDTO;
import com.yemek.sirasi.Dto.RegisterDTO;
import com.yemek.sirasi.Mail.EmailService;
import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo repo;
    private final VerificationTokenService tokenService;
    private final BCryptPasswordEncoder encoder;
    private final EmailService service;

    private static String DEFAULT_ROLE = "ROLE_USER";


    public List<EmployeeDTO> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<EmployeeDTO> employees = repo.findAll().stream().
                map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());

        return employees;
    }


    public void saveEmp(RegisterDTO employeeDto) {
        ModelMapper modelMapper = new ModelMapper();
        checkEmp(employeeDto);
        String pass = encoder.encode(employeeDto.getPassword());
        employeeDto.setPassword(pass);
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setActive(false);
        employee.setRoles(DEFAULT_ROLE);

        Optional<Employee> saved = Optional.of(repo.save(employee));

        saved.ifPresent(e -> {
                    try {
                        String token = UUID.randomUUID().toString();
                        tokenService.save(saved.get(), token);
                        service.sendHtmlMail(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
        });

    }


    public Optional<Employee> getByEmail(String email) {
        return repo.findByEmail(email);
    }


    public void checkEmp(RegisterDTO employee) {
        if (employee.getEmail().isEmpty() || employee.getPassword().isEmpty()
                || employee.getUsername().isEmpty() || !(employee.getPassword().equals(employee.getConfirmPass())))
            throw new RuntimeException("Not Null Employee");
    }


    public void save(Employee employee){
        repo.save(employee);
    }

    public List<Employee> findAll(){
        return repo.findAll();
    }

    public List<String> emails(){
        return  repo.emailGetir();
    }

}
