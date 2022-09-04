package com.yemek.sirasi.Repo;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FoodRepo extends JpaRepository<Food,Long> {

    List<Food> findByTime(LocalDate time);

    @Query(value = "select name from Food where time=:zaman ")
    List<String> yemekGetir(@Param("zaman") LocalDate date);



}
