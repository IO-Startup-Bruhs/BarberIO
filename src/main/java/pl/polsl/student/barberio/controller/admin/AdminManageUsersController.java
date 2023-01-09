package pl.polsl.student.barberio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminManageUsersController {
    @GetMapping("/admin/manageUsers")
    public String view(Model model) {
        return "admin/manageusers";
    }
}
