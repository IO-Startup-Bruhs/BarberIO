package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.student.barberio.form.NewAppointmentForm;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.WorkHours;
import pl.polsl.student.barberio.service.AppointmentService;
import pl.polsl.student.barberio.service.DutyService;
import pl.polsl.student.barberio.service.UserService;
import pl.polsl.student.barberio.service.WorkHoursService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// @Vexisu: I left my soul in this class.
// @VoXel: Here is mine too.
@Controller
@SessionAttributes({"newAppointmentForm"})
public class ClientNewAppointmentController {
    private DutyService dutyService;
    private AppointmentService appointmentService;
    private WorkHoursService workHoursService;

    @GetMapping("/client/newAppointment")
    public String view(Model model) {
        model.addAttribute("newAppointmentForm", new NewAppointmentForm());
        model.addAttribute("availableDuties", dutyService.getAllDuties());
        return "client/newAppointment/duty";
    }

    @PostMapping("/client/newAppointment")
    public String process(@ModelAttribute("newAppointmentForm") NewAppointmentForm newAppointmentForm, @RequestParam(value = "formValue", required = false) String formValue, Model model, @AuthenticationPrincipal DatabaseUserDetails principal) throws ParseException {
        //System.out.println(principal.getUser().getFirstName());
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

                var dateTime = LocalDateTime.of(LocalDate.parse(formValue), LocalTime.of(0,0));
                var date = LocalDate.parse(formValue);
                newAppointmentForm.setDate(date);
                var workHours = new WorkHours();
                workHours = workHoursService.getEmployeeWorkHours(newAppointmentForm.getEmployee().getId());
                var allPossibleHours = appointmentService.returnAllPossibleHours(newAppointmentForm.getEmployee(), dateTime, newAppointmentForm.getDuty().getDuration(),workHours);
                model.addAttribute("allPossibleHours", allPossibleHours);
                newAppointmentForm.setCreationStep(NewAppointmentForm.AppointmentCreationStep.CONFIRM);
                return "client/newAppointment/confirm";
            }
            case CONFIRM -> {
                LocalTime appointmentTime = LocalTime.parse(formValue);
                newAppointmentForm.setTime(appointmentTime);
                this.appointmentService.newAppointmentFromForm(newAppointmentForm, principal.getUser());
                return "redirect:/client/appointments";
            }
        }
        return "redirect:/client/newAppointment";
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
