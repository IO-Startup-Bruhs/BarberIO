package pl.polsl.student.barberio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.service.AppointmentService;
import pl.polsl.student.barberio.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminManageUsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/admin/manageusers")
    public String view(Model model) {
        ArrayList<User> allClients = new ArrayList<>(userService.getUsersWithAuthority("CLIENT"));
        model.addAttribute("allClients", allClients);
        return "admin/manageusers";
    }

    @Transactional
    @GetMapping("/admin/showappointments/{id}")
    public String showUserAppointmentsOfClient(@PathVariable long id, Model model)
    {
        List<Appointment> appointments = appointmentService.getAppointmentsOfUserByID(id);
        model.addAttribute("userAppointments", appointments);
        return "/admin/userappointments";
    }

    @GetMapping("/admin/confirmappointment/{id}")
    public String confirmAppointment(@PathVariable long id, @AuthenticationPrincipal DatabaseUserDetails principal)
    {
        appointmentService.setConfirmed(id, principal);
        //return "/employee/appointments";
        return "redirect:/admin/manageusers";
    }

    @GetMapping("/admin/cancelappointment/{id}")
    public String cancelAppointment(@PathVariable long id, @AuthenticationPrincipal DatabaseUserDetails principal){
        appointmentService.setCancelled(id,principal);
        //return "/employee/appointments";
        return "redirect:/admin/manageusers";
    }
}
