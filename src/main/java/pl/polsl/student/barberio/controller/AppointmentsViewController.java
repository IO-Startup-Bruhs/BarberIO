package pl.polsl.student.barberio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.polsl.student.barberio.model.Appointment;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class AppointmentsViewController {

    @PostMapping("/client/appointments.html")
    public void showTableWithAppointments(List<Appointment> appointments)
    {
        try{
            PrintWriter out = new PrintWriter("/client/appointments.html");

            for (Appointment appointment : appointments)
            {
                out.println("<tr>");
                out.println("  <td>" + appointment.getDate().toString() + "</td>");
                out.println("  <td>" + appointment.getDuty().getName() + "</td>");
                out.println("  <td>" + appointment.getEmployee().getFirstName()+" "+appointment.getEmployee().getLastName() + "</td>");
                if(appointment.isConfirmation())
                {
                    out.println("  <td> Confirmed </td>");
                }else {
                    out.println("  <td> Not confirmed </td>");
                }

                out.println("</tr>");
            }
        }catch(Exception e)
        {

        }


    }

}