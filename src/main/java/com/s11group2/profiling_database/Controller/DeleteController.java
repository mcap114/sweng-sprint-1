package com.s11group2.profiling_database.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeleteController {

    @GetMapping("/deleteunits")
    public String delete() {
        return "deleteunits";
    }
}
