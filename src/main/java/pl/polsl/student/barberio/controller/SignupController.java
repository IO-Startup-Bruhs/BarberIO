package pl.polsl.student.barberio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.student.barberio.form.SignupForm;
import pl.polsl.student.barberio.service.UserService;

import javax.validation.Valid;

@Controller
public class SignupController {
    private UserService userService;

    @GetMapping("/signup")
    public String view(@ModelAttribute("form") SignupForm form, Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("form") @Valid SignupForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()){
            this.userService.signupUser(form);
        }
        return "redirect:/";
    }

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }
}
