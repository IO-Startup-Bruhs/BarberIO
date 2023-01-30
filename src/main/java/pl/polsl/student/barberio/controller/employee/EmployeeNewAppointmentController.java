package pl.polsl.student.barberio.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.polsl.student.barberio.form.EmployeeNewAppointmentForm;
import pl.polsl.student.barberio.form.NewAppointmentForm;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.WorkHours;
import pl.polsl.student.barberio.service.AppointmentService;
import pl.polsl.student.barberio.service.DutyService;
import pl.polsl.student.barberio.service.UserService;
import pl.polsl.student.barberio.service.WorkHoursService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


// @fijalkowskiSeba: Ctrl+C Ctrl+V
@Controller
@SessionAttributes({"newAppointmentForm"})
public class EmployeeNewAppointmentController {

    private DutyService dutyService;
    private AppointmentService appointmentService;
    private WorkHoursService workHoursService;
    private UserService userService;
    @GetMapping("/employee/newAppointment")
    public String view(Model model) {
        model.addAttribute("newAppointmentForm", new EmployeeNewAppointmentForm());
        return "employee/newAppointment/client";
    }


    @PostMapping("/employee/newAppointment")
    public String process(@ModelAttribute("newAppointmentForm") EmployeeNewAppointmentForm newAppointmentForm, @RequestParam(value = "formValue", required = false) String formValue, Model model, @AuthenticationPrincipal DatabaseUserDetails principal) throws ParseException {
        //System.out.println(principal.getUser().getFirstName());
        switch (newAppointmentForm.getCreationStep()) {
            case NEW_CLIENT -> {
                var newUser = new User();
                model.addAttribute("availableDuties", dutyService.getAllDuties());
                newAppointmentForm.setCreationStep(EmployeeNewAppointmentForm.AppointmentCreationStep.SELECT_DUTY);
                return "employee/newAppointment/duty";
            }
            case SELECT_DUTY -> {
                var dutyId = Long.parseLong(formValue);
                var selectedDutyOptional = this.dutyService.getDutyById(dutyId);
                if (selectedDutyOptional.isEmpty()) {
                    return "redirect:/employee/newAppointment";
                }
                var selectedDuty = selectedDutyOptional.get();
                newAppointmentForm.setDuty(selectedDuty);
                newAppointmentForm.setCreationStep(EmployeeNewAppointmentForm.AppointmentCreationStep.SELECT_EMPLOYEE);
                model.addAttribute("availableEmployees", selectedDuty.getDoneBy());
                return "employee/newAppointment/employee";
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
                    return "redirect:/employee/newAppointment";
                }
                newAppointmentForm.setEmployee(employee);
                newAppointmentForm.setCreationStep(EmployeeNewAppointmentForm.AppointmentCreationStep.SELECT_DATE);
                return "employee/newAppointment/date";
            }
            case SELECT_DATE -> {

                var dateTime = LocalDateTime.of(LocalDate.parse(formValue), LocalTime.of(0,0));
                var date = LocalDate.parse(formValue);
                newAppointmentForm.setDate(date);
                var workHours = new WorkHours();
                workHours = workHoursService.getEmployeeWorkHours(newAppointmentForm.getEmployee().getId());
                var allPossibleHours = appointmentService.returnAllPossibleHours(newAppointmentForm.getEmployee(), dateTime, newAppointmentForm.getDuty().getDuration(),workHours);
                model.addAttribute("allPossibleHours", allPossibleHours);
                newAppointmentForm.setCreationStep(EmployeeNewAppointmentForm.AppointmentCreationStep.CONFIRM);
                return "employee/newAppointment/confirm";
            }
            case CONFIRM -> {
                LocalTime appointmentTime = LocalTime.parse(formValue);
                newAppointmentForm.setTime(appointmentTime);
                var client = new User();
                client.setFirstName(newAppointmentForm.getFirstName());
                client.setLastName(newAppointmentForm.getLastName());
                client.setPhoneNumber(newAppointmentForm.getPhoneNumber());
                this.userService.newClient(client);
                this.appointmentService.newAppointmentFromForm(newAppointmentForm, client);
                return "redirect:/employee/appointments";
            }
        }
        return "redirect:/employee/newAppointment";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
    @Autowired
    public void setWorkHoursService(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }
}