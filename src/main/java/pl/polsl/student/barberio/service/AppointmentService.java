package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.repository.AppointmentRepository;

import java.util.*;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;


    public void setCancelled(long appointmentId){
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {
            a.setCancelled(true);
            appointmentRepository.save(a);
        });

    }

    public void setConfirmed(long appointmentId){
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {
            a.setConfirmation(true);
            appointmentRepository.save(a);
        });

    }


    public List<Appointment> getAppointments(User customer) {
        return appointmentRepository.findByCustomerId(customer.getId());
    }

    public List<Appointment> getAppointments() {
        Iterable<Appointment> iterable = appointmentRepository.findAll();
        List<Appointment> list = new ArrayList<>((Collection<Appointment>) iterable);
        //może można zwrócić i używać iterable ?
        return list;
    }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository){
        this.appointmentRepository=appointmentRepository;}
}
