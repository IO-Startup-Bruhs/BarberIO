package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.polsl.student.barberio.form.DutyForm;

import pl.polsl.student.barberio.service.UserService;

@Controller
public class AdminManageSalonController {

    private UserService userService;

    @GetMapping("/admin/managesalon")
    public String view(@ModelAttribute("form") DutyForm form, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("ROLE_EMPLOYEE"));
        return "admin/managesalon";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
