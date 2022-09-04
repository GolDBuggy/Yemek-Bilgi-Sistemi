package com.yemek.sirasi.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private long id;

    @Column(name = "food_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String image;

    @Column(name = "created_time")
    private LocalDate time;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "katilim",joinColumns = @JoinColumn(name = "yemek_id"),inverseJoinColumns = @JoinColumn(name = "calisan_id"))
    List<Employee> employees;


}
