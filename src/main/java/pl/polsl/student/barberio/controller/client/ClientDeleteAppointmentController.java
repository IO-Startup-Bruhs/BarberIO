package pl.polsl.student.barberio.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.AppointmentService;

import javax.transaction.Transactional;

@Controller
public class ClientDeleteAppointmentController {
    private AppointmentService appointmentService;
    @Transactional
    @GetMapping("/client/appointments/{appointmentId}/{customerId}/remove")
    public String deleteAppointment(@PathVariable long appointmentId, @PathVariable long customerId)
    {
        this.appointmentService.deleteAppointment(appointmentId, customerId);
        return "redirect:/client/appointments/";
    }

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }
}
