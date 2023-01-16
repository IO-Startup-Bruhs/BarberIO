package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.student.barberio.form.NewAppointmentForm;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.DutyService;
import pl.polsl.student.barberio.service.UserService;

// @Vexisu: I left my soul in this class.
@Controller
public class ClientNewAppointmentController {
    private DutyService dutyService;
    private UserService userService;

    @GetMapping("/client/newAppointment")
    public String view(Model model) {
        model.addAttribute("newAppointmentForm", new NewAppointmentForm());
        model.addAttribute("availableDuties", dutyService.getAllDuties());
        return "client/newAppointment/duty";
    }

    @PostMapping("/client/newAppointment")
    public String process(@ModelAttribute("newAppointmentForm") NewAppointmentForm newAppointmentForm, @RequestParam("formValue") String formValue, Model model) {
        if (formValue == null || formValue.isEmpty()) {
            return "redirect:/client/newAppointment";
        }
        switch (newAppointmentForm.getCreationStep()) {
            case SELECT_DUTY -> {
                var dutyId = Long.parseLong(formValue);
                var selectedDutyOptional = this.dutyService.getDutyById(dutyId);
                if (selectedDutyOptional.isEmpty()) {
                    return "redirect:/client/newAppointment";
                }
                var selectedDuty = selectedDutyOptional.get();
                newAppointmentForm.setDuty(selectedDuty);
                newAppointmentForm.setCreationStep(NewAppointmentForm.AppointmentCreationStep.SELECT_EMPLOYEE);
                model.addAttribute("availableEmployees", selectedDuty.getDoneBy());
                return "client/newAppointment/employee";
            }
            case SELECT_EMPLOYEE -> {
                var employeeId = Long.parseLong(formValue);
                User employee = null;
                for (User e : newAppointmentForm.getDuty().getDoneBy()) {
                    if (e.getId() == employeeId) {
                        employee = e;
                        break;
                    }
                }
                if (employee == null) {
                    return "redirect:/client/newAppointment";
                }
                newAppointmentForm.setEmployee(employee);
                newAppointmentForm.setCreationStep(NewAppointmentForm.AppointmentCreationStep.SELECT_DATE);
                return "client/newAppointment/date";
            }
            case SELECT_DATE -> {
                newAppointmentForm.setCreationStep(NewAppointmentForm.AppointmentCreationStep.CONFIRM);
                return "client/newAppointment/confirm";
            }
            case CONFIRM -> {
                return "redirect:/client/appointments";
            }
        }
        return "redirect:/client/newAppointment";
    }

    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
}
