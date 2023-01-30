package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.AppointmentService;
import pl.polsl.student.barberio.service.DutyService;

@Controller
public class ClientAppointmentsController {
    private AppointmentService appointmentService;
    private DutyService dutyService;

    @GetMapping("/client/appointments")
    public String showTableWithAppointments(Model model, User user) {
        model.addAttribute("appointments", this.appointmentService.getAppointmentsOfUser(user));
        return "/client/appointments";
    }

    //TODO clear up setters
    @Autowired
    public void setAppointmentService(AppointmentService appointmentService, DutyService dutyService) {
        this.appointmentService = appointmentService;
        this.dutyService = dutyService;
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
}