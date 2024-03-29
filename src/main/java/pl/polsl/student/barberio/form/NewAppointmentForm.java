package pl.polsl.student.barberio.form;

import lombok.Data;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class NewAppointmentForm {
    private AppointmentCreationStep creationStep = AppointmentCreationStep.SELECT_DUTY;
    private Duty duty;
    private User employee;
    private LocalDate date;
    private LocalTime time;

    public enum AppointmentCreationStep {
        SELECT_DUTY,
        SELECT_EMPLOYEE,
        SELECT_DATE,
        CONFIRM
    }
}
