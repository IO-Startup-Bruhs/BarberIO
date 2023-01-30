package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.service.AppointmentService;

@Controller
public class ClientAppointmentsController {
    private AppointmentService appointmentService;

    @GetMapping("/client/appointments")
    public String showTableWithAppointments(Model model, @AuthenticationPrincipal DatabaseUserDetails principal) {
        model.addAttribute("appointments",this.appointmentService.getAppointments(principal.getUser()));
        return "/client/userAppointments";
    }

    @GetMapping("/cancelAppointment/client/{id}")
    public String cancelAppointment(@PathVariable long id, @AuthenticationPrincipal DatabaseUserDetails principal){
        appointmentService.setCancelled(id,principal);
        return "redirect:/client/appointments";
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
}