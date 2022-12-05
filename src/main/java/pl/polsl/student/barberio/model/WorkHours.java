package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class WorkHours {
    @Id
    private long id;
    private String combinedDates;
    @ManyToOne
    private User employee;
}
