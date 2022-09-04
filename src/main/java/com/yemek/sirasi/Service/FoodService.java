package com.yemek.sirasi.Service;

import com.yemek.sirasi.Dto.DailyMealDto;
import com.yemek.sirasi.Dto.EmployeeDTO;
import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.Food;
import com.yemek.sirasi.Repo.EmployeeRepo;
import com.yemek.sirasi.Repo.FoodRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepo repo;
    private final EmployeeService service;
    private Logger logger = Logger.getLogger(FoodService.class.getName());

    public List<Food> getAll() {
        return repo.findAll();
    }

    public List<Food> getByToday(){
        return repo.findByTime(LocalDate.now());
    }

    public String foodNames(){
        StringBuilder stringBuilder=new StringBuilder("Günün Menüsü => ");
        repo.yemekGetir(LocalDate.now()).stream().forEach(e -> stringBuilder.append(e+" "));
        logger.info(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void saveFood(Food food) {
        food.setTime(LocalDate.now());
        repo.save(food);
    }


    public void likeFood(Long foodId, Principal principal) {
        Food food = repo.findById(foodId).get();
        food.getEmployees().add(service.getByEmail(principal.getName()).get());
        repo.save(food);
    }

    public Map<String, List> findEmployeeByToday(){
        Map<String,List> gelenler=new HashMap<>();
        service.findAll().stream().forEach(u -> {
            u.getFoods().stream().filter(num -> num.getTime().isEqual(LocalDate.now())).forEach(e ->{
                gelenler.put(u.getEmpName(),u.getFoods().stream().filter(num -> num.getTime().isEqual(LocalDate.now())).collect(Collectors.toList()));
            });
        });

        return gelenler;
    }

}

/*
   public List<Employee> findEmployeeByToday(){
        List<Employee> gelenler=new ArrayList<>();
        repo.findByTime(LocalDateTime.now()).stream().forEach(e ->{
            e.getEmployees().stream().forEach(u ->{
                if(!gelenler.contains(u)){
                    gelenler.add(u);
                }
            });
        });

        return gelenler;
    }
 */



/*
    public List<DailyMealDto> findEmployeeByToday(){
        List<DailyMealDto> gelenler=new ArrayList<>();
        service.findAll().stream().forEach(u -> {
            u.getFoods().stream().forEach(e ->{
                        gelenler.add(new DailyMealDto(u.getEmpName(),
                                u.getFoods().stream().filter(num -> num.getTime().isEqual(LocalDate.now())).collect(Collectors.toList())));

            });
        });


        return gelenler;
    }
 */


/*
 public List<Employee> findEmployeeByToday(){
        List<Employee> gelenler=new ArrayList<>();
        repo.findByTime(LocalDateTime.now()).stream().forEach(e ->{
            e.getEmployees().stream().forEach(u -> gelenler.add(u));
        });

        return gelenler;
    }
 */
