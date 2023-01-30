package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.repository.AppointmentRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointmentsOfUser(User customer)
    {
        //TODO repair
        return appointmentRepository.findByCustomerId(3/*customer.getId()*/);
    }

    @Transactional
    public void deleteAppointment(Long appointmentId, long customerId)
    {
        //TODO repair
        appointmentRepository.deleteAppointmentById(appointmentId);
    }

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
}
