package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User customer;
    @ManyToOne
    private User employee;
    @ManyToOne
    private Duty duty;
    private Date date;
    private boolean confirmation;
}
