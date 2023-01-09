package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.UserAuthority;

import java.util.List;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {
    List<UserAuthority> getAllByUserId(long userId);
}
