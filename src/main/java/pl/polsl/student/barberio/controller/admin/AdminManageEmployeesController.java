package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.student.barberio.form.NewEmployeeForm;
import pl.polsl.student.barberio.form.SignupForm;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class AdminManageEmployeesController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/manageemployees")
    public String view(Model model) {
        ArrayList<User> allEmployees =
                new ArrayList<>(userService.getUsersWithAuthority("EMPLOYEE"));
        model.addAttribute("allEmployees",allEmployees);
        return "admin/manageemployees";
    }

    @GetMapping("/admin/useroperations/newemployee")
    public String newEmployeeView(@ModelAttribute("form") NewEmployeeForm form,Model model){

        return "/admin/useroperations/newemployee";
    }

    @PostMapping("/admin/useroperations/newemployee")
    public String signup(@ModelAttribute("form") @Valid NewEmployeeForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            var registeredUserOptional = this.userService.newEmployee(form);
            if (registeredUserOptional.isPresent()) {
                return "redirect:/";
            }
            bindingResult.addError(new ObjectError("email", "User with this email already exists."));
        }
        return "/admin/useroperations/newemployee";
    }

}
