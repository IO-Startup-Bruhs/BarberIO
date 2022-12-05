package pl.polsl.student.barberio.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {
    @Id
    private long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Size(min = 9, max = 9)
    private String phoneNumber;
    @Email
    private String email;
    private String password;
}
