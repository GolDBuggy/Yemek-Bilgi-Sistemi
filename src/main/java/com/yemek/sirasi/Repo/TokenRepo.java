package com.yemek.sirasi.Repo;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByEmployee(Employee employee);
}
