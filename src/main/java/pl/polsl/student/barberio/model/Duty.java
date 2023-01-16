package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    @Size(min = 3, max = 64)
    private String name;
    @Min(10)
    private int price;
    /**
     * Duration time of appointment in minutes.
     */
    @Min(30)
    private int duration;
    @ManyToMany
    @NotNull
    @NotEmpty
    private List<User> doneBy=new ArrayList<>();
}
