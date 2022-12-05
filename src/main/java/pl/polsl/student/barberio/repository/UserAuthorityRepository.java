package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.UserAuthority;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {
}
