package pl.polsl.student.barberio.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.polsl.student.barberio.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u INNER JOIN UserAuthority ua ON u.id = ua.userId WHERE ua.role = :authority")
    List<User> getUsersWithAuthority(@Param("authority") String authority);

    boolean existsByEmail(String email);

    User getUserByEmail(String email);
}
