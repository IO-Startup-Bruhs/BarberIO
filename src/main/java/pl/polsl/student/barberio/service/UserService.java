package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.form.SignupForm;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.UserAuthority;
import pl.polsl.student.barberio.repository.UserAuthorityRepository;
import pl.polsl.student.barberio.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User signupUser(SignupForm form) {
        var user = new User();
        user.setEmail(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setPhoneNumber(form.getPhoneNumber());
        var encodedPassword = this.passwordEncoder.encode(form.getPassword());
        user.setPassword(encodedPassword);
        user = this.userRepository.save(user);
        var userAuthority = new UserAuthority();
        userAuthority.setUserId(user.getId());
        userAuthority.setRole("ROLE_CLIENT");
        this.userAuthorityRepository.save(userAuthority);
        return user;
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
