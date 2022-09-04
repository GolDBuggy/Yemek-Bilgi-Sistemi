package com.yemek.sirasi.Service;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDetailService implements UserDetailsService {

    private final EmployeeRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee=repo.findByEmail(email);
        return employee.map(EmployeeDetails::new).orElseThrow(() -> new UsernameNotFoundException("Kullanıcı Bulunmadı!"));
    }
}
