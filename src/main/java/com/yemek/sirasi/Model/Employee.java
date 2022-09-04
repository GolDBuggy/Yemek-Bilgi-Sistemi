package com.yemek.sirasi.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "nar_calisan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "employee_pass")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "employee_name")
    private String empName;

    @Column(name = "roles")
    private String roles;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "katilim",joinColumns = @JoinColumn(name = "calisan_id"),inverseJoinColumns = @JoinColumn(name = "yemek_id"))
    private List<Food> foods;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private VerificationToken verificationToken;
}
