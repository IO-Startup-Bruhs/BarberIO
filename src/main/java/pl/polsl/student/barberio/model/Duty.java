package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Duty {
    @Id
    private long id;
    private String name;
    private int price;
    /**
     * Duration time of appointment in minutes.
     */
    private int duration;
    @ManyToMany
    private List<User> doneBy;
}
