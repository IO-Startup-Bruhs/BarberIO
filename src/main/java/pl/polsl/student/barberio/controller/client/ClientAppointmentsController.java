package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.AppointmentService;

@Controller
public class ClientAppointmentsController {
    private AppointmentService appointmentService;

    @GetMapping("/client/appointments")
    public String showTableWithAppointments(Model model, User user) {
        model.addAttribute(this.appointmentService.getAppointments(user));
        return "client/appointments";
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
}