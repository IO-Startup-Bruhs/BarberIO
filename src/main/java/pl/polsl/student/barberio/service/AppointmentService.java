package pl.polsl.student.barberio.service;

import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    public List<Appointment> getAppointments(User customer)
    {
        return appointmentRepository.findByCustomerId(customer.getId());
    }
}
