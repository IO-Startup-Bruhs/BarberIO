package pl.polsl.student.barberio.form;

import lombok.Data;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EmployeeNewAppointmentForm {
    private EmployeeNewAppointmentForm.AppointmentCreationStep creationStep =
            AppointmentCreationStep.NEW_CLIENT;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Duty duty;
    private User employee;
    private LocalDate date;
    private LocalTime time;

    public enum AppointmentCreationStep {

        SELECT_DUTY,
        SELECT_EMPLOYEE,
        SELECT_DATE,
        CONFIRM,
        NEW_CLIENT
    }
}
