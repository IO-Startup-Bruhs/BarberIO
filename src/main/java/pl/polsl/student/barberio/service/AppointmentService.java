package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.polsl.student.barberio.form.NewAppointmentForm;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.repository.AppointmentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    public void setCancelled(long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {
            a.setCancelled(true);
            appointmentRepository.save(a);
        });

    }

    public void setConfirmed(long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {
            a.setConfirmation(true);
            appointmentRepository.save(a);
        });

    }


    public List<Appointment> getAppointments(User customer) {
        return appointmentRepository.findByCustomerId(customer.getId());
    }

    @Transactional
    public void deleteAppointment(long appointmentId, long customerId)
    {
        appointmentRepository.deleteAppointmentById(appointmentId);
    }

    public List<Appointment> getAppointments() {
        Iterable<Appointment> iterable = appointmentRepository.findAll();
        List<Appointment> list = new ArrayList<>((Collection<Appointment>) iterable);
        //może można zwrócić i używać iterable ?
        return list;
    }

    public void newAppointmentFromForm(NewAppointmentForm form, User customer) {
        var appointment = new Appointment();
        appointment.setEmployee(form.getEmployee());
        appointment.setDuty(form.getDuty());
        appointment.setDate(form.getDate());
        appointment.setCustomer(customer);
        appointmentRepository.save(appointment);
    }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
}
