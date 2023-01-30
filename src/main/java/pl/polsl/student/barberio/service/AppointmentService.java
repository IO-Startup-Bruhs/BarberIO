package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.form.EmployeeNewAppointmentForm;
import pl.polsl.student.barberio.form.NewAppointmentForm;
import pl.polsl.student.barberio.impl.DatabaseUserDetails;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.WorkHours;
import pl.polsl.student.barberio.repository.AppointmentRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    public void setCancelled(long appointmentId, DatabaseUserDetails principal) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {

            if((principal.getUser().getId() == a.getCustomer().getId())
                    || (principal.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
                    || (principal.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))))
            {
                a.setCancelled(true);
                appointmentRepository.save(a);
            }

        });

    }

    public void setConfirmed(long appointmentId, DatabaseUserDetails principal) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        appointment.ifPresent(a -> {
            if(principal.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))
                    || principal.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE")))
            {
                a.setConfirmation(true);
                appointmentRepository.save(a);
            }
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

    public void newAppointmentFromForm(NewAppointmentForm form, User customer) {
        var appointment = new Appointment();
        appointment.setEmployee(form.getEmployee());
        appointment.setDuty(form.getDuty());
        appointment.setDate(LocalDateTime.of(form.getDate(), form.getTime()));
        appointment.setCustomer(customer);
        appointmentRepository.save(appointment);
    }

    public void newAppointmentFromForm(EmployeeNewAppointmentForm form, User customer) {
        var appointment = new Appointment();
        appointment.setEmployee(form.getEmployee());
        appointment.setDuty(form.getDuty());
        appointment.setDate(LocalDateTime.of(form.getDate(), form.getTime()));
        appointment.setCustomer(customer);
        appointmentRepository.save(appointment);
    }

    public ArrayList<Appointment> getAllEmployeeAppointments(long employeeId){
        ArrayList<Appointment> employeeAppointments = new ArrayList<>();
        for(Appointment appointment: appointmentRepository.findAll()){
            if( appointment.getEmployee().getId() == employeeId){
                employeeAppointments.add(appointment);
            }
        }
        return employeeAppointments;
    }

    public ArrayList<Appointment> filterAndSortAppointmentsByDate(LocalDateTime filteringDateTime, ArrayList<Appointment> employeeAppointments){
        ArrayList<Appointment> filteredAndSortedAppointments = new ArrayList<Appointment>();
        for(Appointment appointment: employeeAppointments){
            if(appointment.getDate().getDayOfYear() == filteringDateTime.getDayOfYear() && appointment.isCancelled() == false){

                filteredAndSortedAppointments.add(appointment);
            }
        }
        Collections.sort(filteredAndSortedAppointments, Comparator.comparing(Appointment::getDate));
        return filteredAndSortedAppointments;
    }

    public List<LocalTime> returnAllPossibleHours(User employee, LocalDateTime dateOfAppointment, int dutyDuration, WorkHours workHours){
        List<LocalTime> possibleHours = new ArrayList<LocalTime>();
        var allEmployeeAppointments = getAllEmployeeAppointments(employee.getId());
        var sortedAppointments = filterAndSortAppointmentsByDate(dateOfAppointment,allEmployeeAppointments);
        HashMap<LocalTime, LocalTime> freeTimePeriods = new HashMap<>();
        LocalTime workStart = LocalTime.of(workHours.getStartHour(),0);
        LocalTime workEnd = LocalTime.of(workHours.getFinishHour(),0);

        if(sortedAppointments.size() > 0){

            for(int i = 0; i <= sortedAppointments.size(); i++) {
                if (i == 0) {
                    var start = workStart;
                    var end = sortedAppointments.get(0).getDate().toLocalTime();
                    freeTimePeriods.put(start, end);
                } else if (i == sortedAppointments.size()) {
                    var minutesOfCurrentDuty = sortedAppointments.get(i - 1).getDuty().getDuration();
                    var start = sortedAppointments.get(i - 1).getDate().toLocalTime().plusMinutes(minutesOfCurrentDuty);
                    var end = workEnd;
                    freeTimePeriods.put(start, end);
                } else {
                    var minutesOfCurrentDuty = sortedAppointments.get(i - 1).getDuty().getDuration();
                    var start = sortedAppointments.get(i - 1).getDate().toLocalTime().plusMinutes(minutesOfCurrentDuty);
                    var end = sortedAppointments.get(i).getDate().toLocalTime();
                    freeTimePeriods.put(start, end);
                }
            }

        }
        else{
            freeTimePeriods.put(workStart,workEnd);
        }

        for (Map.Entry<LocalTime, LocalTime> entry : freeTimePeriods.entrySet()) {
            var start = entry.getKey();
            var end = entry.getValue();
            var freeTimePeriod = ChronoUnit.MINUTES.between(start, end);
            if(freeTimePeriod > dutyDuration){
                for(int i = 0; i <= freeTimePeriod - dutyDuration; i+=15){
                    possibleHours.add(start.plusMinutes(i));
                }
            }
        }
        Collections.sort(possibleHours);
        return possibleHours;
    }


    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
}
