package pl.polsl.student.barberio.form;

import lombok.Data;
import pl.polsl.student.barberio.model.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DutyForm {
    @NotEmpty
    @Size(min = 3, max = 64)
    private String name;
    @Min(10)
    private int price;
    @Min(30)
    private int duration;
    @NotNull
    @NotEmpty
    private List<User> doneBy;

}
