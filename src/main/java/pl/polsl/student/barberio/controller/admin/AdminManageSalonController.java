package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.student.barberio.form.DutyForm;

import pl.polsl.student.barberio.service.DutyService;
import pl.polsl.student.barberio.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminManageSalonController {

    private UserService userService;
    private DutyService dutyService;

    @GetMapping("/admin/managesalon")
    public String view(@ModelAttribute("form") DutyForm form, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("ROLE_EMPLOYEE"));
        return "admin/managesalon";
    }
    @PostMapping("/admin/managesalon")
    public String addDuty(@ModelAttribute("form") @Valid DutyForm form, BindingResult bindingResult, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("ROLE_EMPLOYEE"));
        if (!bindingResult.hasErrors()){
            this.dutyService.createDuty(form);
            return "redirect:/admin/managesalon";
        }
        return "admin/managesalon";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
}
