package com.s11group2.profiling_database.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        //model.addAttribute("message", "test home page");
        return "home";
    }
}
