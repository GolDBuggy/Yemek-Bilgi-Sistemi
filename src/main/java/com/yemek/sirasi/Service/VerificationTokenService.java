package com.yemek.sirasi.Service;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.VerificationToken;
import com.yemek.sirasi.Repo.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final TokenRepo repo;

    public VerificationToken findByToken(String token){
        return repo.findByToken(token);
    }

    public VerificationToken findByEmployee(Employee employee){
        return repo.findByEmployee(employee);
    }

    public void save(Employee employee,String token){
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setEmployee(employee);
        verificationToken.setExpiryDate(calculateDate(24*60));
        repo.save(verificationToken);
    }


    private Timestamp calculateDate(int timeMinutes){
        Calendar calendar=Calendar.getInstance();
        calendar.add(calendar.MINUTE,timeMinutes);

        return new Timestamp(calendar.getTime().getTime());
    }
}
