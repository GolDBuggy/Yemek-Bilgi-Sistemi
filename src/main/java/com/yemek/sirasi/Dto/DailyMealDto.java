package com.yemek.sirasi.Dto;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMealDto {

    private String empName;
    private List<Food> foods;
}
