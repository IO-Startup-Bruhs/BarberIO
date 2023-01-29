package pl.polsl.student.barberio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Error {

    @GetMapping("/error")
    public String view(Model model) {
        return "error";
    }
}
