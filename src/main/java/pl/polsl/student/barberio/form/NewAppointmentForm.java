package pl.polsl.student.barberio.form;

import lombok.Data;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.model.User;

import java.util.Date;

@Data
public class NewAppointmentForm {
    private AppointmentCreationStep creationStep = AppointmentCreationStep.SELECT_DUTY;
    private Duty duty;
    private User employee;
    private Date date;

    public enum AppointmentCreationStep {
        SELECT_DUTY,
        SELECT_EMPLOYEE,
        SELECT_DATE,
        CONFIRM
    }
}
