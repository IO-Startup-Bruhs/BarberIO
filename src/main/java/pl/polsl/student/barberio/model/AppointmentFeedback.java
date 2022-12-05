package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class AppointmentFeedback {
    @Id
    private long id;
    private String feedback;
    private int rating;
}
