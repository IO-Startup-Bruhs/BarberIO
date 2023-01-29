package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.student.barberio.form.DutyForm;
import pl.polsl.student.barberio.form.NewEmployeeForm;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.service.DutyService;
import pl.polsl.student.barberio.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class AdminManageSalonController {

    @Autowired
    private UserService userService;
    @Autowired
    private DutyService dutyService;

    @GetMapping("/admin/managesalon")
    public String view(@ModelAttribute("form") DutyForm form, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("EMPLOYEE"));
        ArrayList<Duty> allDuties = new ArrayList<>();
        Iterable<Duty> iterable= dutyService.getAllDuties();
        iterable.forEach(allDuties::add);
        model.addAttribute("allDuties", allDuties);
        return "admin/managesalon";
    }


    @GetMapping("/admin/dutyoperations/newduty")
    public String addDuty(@ModelAttribute("form") DutyForm form, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("EMPLOYEE"));
        return "/admin/dutyoperations/newduty";
    }
    @PostMapping("/admin/dutyoperations/newduty")
    public String addDuty(@ModelAttribute("form") @Valid DutyForm form, BindingResult bindingResult, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("EMPLOYEE"));
        if (!bindingResult.hasErrors()) {
            this.dutyService.createDuty(form);
            return "redirect:/admin/managesalon";
        }
        return "/admin/dutyoperations/newduty";
    }

    @GetMapping("admin/dutyoperations/updateduty/{id}")
    public String updateDuty(@PathVariable long id, @ModelAttribute("form") Duty form, Model model) {
        model.addAttribute("employees", userService.getUsersWithAuthority("EMPLOYEE"));
        var duty = dutyService.getDutyById(id);
        duty.ifPresent(d -> {
            form.setId(id);
            form.setName(d.getName());
            form.setPrice(d.getPrice());
            form.setDuration(d.getDuration());
            form.setDoneBy(d.getDoneBy());
        });
        return "/admin/dutyoperations/updateduty";
    }

    @PostMapping("admin/dutyoperations/updateduty/{id}")
    public String updateDuty(@ModelAttribute("form") @Valid DutyForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            var duty = this.dutyService.updateDuty(form);
            if (duty.isPresent()) {
                return "redirect:/admin/managesalon";
            }
            //bindingResult.addError(new ObjectError("email", "User with this email already exists."));
        }
        return "redirect:/admin/managesalon";
    }


}
