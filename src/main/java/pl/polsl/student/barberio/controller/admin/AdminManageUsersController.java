package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.AppointmentService;
import pl.polsl.student.barberio.service.UserService;

import java.util.ArrayList;

@Controller
public class AdminManageUsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/manageusers")
    public String view(Model model) {
        ArrayList<User> allClients = new ArrayList<>(userService.getUsersWithAuthority("CLIENT"));
        model.addAttribute("allClients", allClients);
        return "admin/manageusers";
    }
}
