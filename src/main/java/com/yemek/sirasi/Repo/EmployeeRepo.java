package com.yemek.sirasi.Repo;

import com.yemek.sirasi.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    public Optional<Employee> findByEmail(String email);

    @Query(value = "select email from Employee")
    public List<String> emailGetir();
}
