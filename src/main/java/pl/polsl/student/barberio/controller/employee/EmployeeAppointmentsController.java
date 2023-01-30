package pl.polsl.student.barberio.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.service.AppointmentService;

import java.util.ArrayList;

@Controller
public class EmployeeAppointmentsController {

    private AppointmentService appointmentService;
    @GetMapping("/employee/appointments")
    public String view(Model model) {
        ArrayList<Appointment> list = new ArrayList<>(appointmentService.getAppointments());
        model.addAttribute("allAppointments", list);
        return "employee/appointments";
    }

    @GetMapping("/cancelAppointment/{id}")
    public String cancelAppointment(@PathVariable long id, @AuthenticationPrincipal DatabaseUserDetails principal){
        appointmentService.setCancelled(id,principal);
        return "redirect:/employee/appointments";
    }

    @GetMapping("/confirmAppointment/{id}")
    public String confirmAppointment(@PathVariable long id, @AuthenticationPrincipal DatabaseUserDetails principal){
        appointmentService.setConfirmed(id, principal);
        return "redirect:/employee/appointments";
    }


    @Autowired
    public  void setAppointmentService(AppointmentService appointmentService){
        this.appointmentService=appointmentService;}
}
