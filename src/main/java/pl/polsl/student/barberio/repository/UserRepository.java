package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
