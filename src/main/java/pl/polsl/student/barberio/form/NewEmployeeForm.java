package pl.polsl.student.barberio.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NewEmployeeForm {
    private long id;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(max = 32)
    private String firstName;
    @NotEmpty
    @Size(max = 32)
    private String lastName;
    @Size(min = 9, max = 9)
    private String phoneNumber;
    @Size(min = 8, max = 64)
    private String password;
    @Size(min = 8, max = 64)
    private String confirmPassword;
}
