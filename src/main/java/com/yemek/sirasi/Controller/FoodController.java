package com.yemek.sirasi.Controller;

import com.yemek.sirasi.Model.Employee;
import com.yemek.sirasi.Model.Food;
import com.yemek.sirasi.Service.EmployeeService;
import com.yemek.sirasi.Service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;

    private static Logger logger=Logger.getLogger(FoodController.class.getName());

    @PostMapping("/save")
    public String saveFood(@ModelAttribute("food")Food food, Principal principal){
        logger.info(principal.getName()+"");
        service.saveFood(food);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String yemekleriListele(Model model){
      model.addAttribute("foods",service.getByToday());
      return "food-list";
    }

    @GetMapping("/food")
    public String getHell(Model model){
        model.addAttribute(new Food());
        model.addAttribute("gelenler",service.findEmployeeByToday());
        return "food-add";
    }


    @PostMapping("/join")
    public String likeFood(@RequestBody String id,Principal principal){
        id=id.substring(3);
        service.likeFood(Long.valueOf(id),principal);
        return "redirect:/list";
    }






}
