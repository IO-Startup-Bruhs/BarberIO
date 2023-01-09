package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.repository.AppointmentRepository;


import java.util.List;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointmentsOfUser(User customer)
    {
        return appointmentRepository.findByCustomerId(customer.getId());
    }

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
}
