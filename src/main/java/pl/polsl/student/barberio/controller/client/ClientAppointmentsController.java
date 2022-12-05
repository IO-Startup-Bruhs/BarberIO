package pl.polsl.student.barberio.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientAppointmentsController {
    @GetMapping("/client/appointments")
    public String view(Model model){return "client/appointments";}
}
