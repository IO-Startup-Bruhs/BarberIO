package pl.polsl.student.barberio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserAuthority {

    @Id
    private long id;
    private long userId;
    private String role;
}
