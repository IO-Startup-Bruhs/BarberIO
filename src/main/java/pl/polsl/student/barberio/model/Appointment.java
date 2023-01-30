package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime date;
    private boolean confirmation = false;
    private boolean cancelled = false;
    @OneToOne
    private AppointmentFeedback feedback;
}
