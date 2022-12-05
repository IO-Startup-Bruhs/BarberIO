package pl.polsl.student.barberio.controller.employee;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeNewAppointmentController {
    @GetMapping("/employee/newAppointment")
    public String view(Model model){return "employee/newappointment";}
}
