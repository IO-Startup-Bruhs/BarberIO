package pl.polsl.student.barberio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.UserAuthority;
import pl.polsl.student.barberio.repository.UserAuthorityRepository;
import pl.polsl.student.barberio.repository.UserRepository;

@Configuration
@Profile("devel")
public class DevelopmentConfig {
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;
    @Bean
    public void addTestUsers(){
        User user = new User();
        user.setFirstName("Tom");
        user.setLastName("Lee");
        user.setPassword("password");
        user.setEmail("tom.lee@mail.com");
        userRepository.save(user);

        User user2 = new User();
        user2.setFirstName("Tom");
        user2.setLastName("Holland");
        user2.setPassword("password");
        user2.setEmail("tom.holland@mail.com");
        userRepository.save(user2);

        UserAuthority userAuthorityEmployee = new UserAuthority();
        userAuthorityEmployee.setRole("ROLE_EMPLOYEE");
        userAuthorityEmployee.setUserId(user.getId());
        userAuthorityRepository.save(userAuthorityEmployee);

        UserAuthority userAuthorityClient = new UserAuthority();
        userAuthorityClient.setRole("ROLE_CLIENT");
        userAuthorityClient.setUserId(user2.getId());
        userAuthorityRepository.save(userAuthorityClient);


    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }
}
