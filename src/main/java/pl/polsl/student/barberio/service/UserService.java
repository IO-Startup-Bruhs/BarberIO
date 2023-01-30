package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.form.NewEmployeeForm;
import pl.polsl.student.barberio.form.SignupForm;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.UserAuthority;
import pl.polsl.student.barberio.repository.UserAuthorityRepository;
import pl.polsl.student.barberio.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;

    public Optional<User> signupClient(SignupForm form) {
        if (this.userRepository.existsByEmail(form.getEmail())) {
            return Optional.empty();
        }
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
        userAuthority.setRole("CLIENT");
        this.userAuthorityRepository.save(userAuthority);
        return Optional.of(user);
    }

    public Optional<User> newEmployee(NewEmployeeForm form){
        if (this.userRepository.existsByEmail(form.getEmail())) {
            return Optional.empty();
        }
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
        userAuthority.setRole("EMPLOYEE");
        this.userAuthorityRepository.save(userAuthority);
        return Optional.of(user);
    }

    public Optional<User> updateUser(NewEmployeeForm form){

        var user =userRepository.findById(form.getId());
        //var user = userRepository.getUserById(form.getId());

        user.ifPresent(u->
        {
            u.setEmail(form.getEmail());
            var encodedPassword = passwordEncoder.encode(form.getPassword());
            u.setPassword(encodedPassword);
            u.setFirstName(form.getFirstName());
            u.setLastName(form.getLastName());
            u.setPhoneNumber(form.getPhoneNumber());
            this.userRepository.save(u);
        });

        return user;

    }
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(this.userRepository.getUserByEmail(email));
    }

    public List<UserAuthority> getUsersAuthorities(User user) {
        return this.userAuthorityRepository.getAllByUserId(user.getId());
    }

    public User newClient(User user){

        this.userRepository.save(user);
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserId(user.getId());
        userAuthority.setRole("CLIENT");
        this.userAuthorityRepository.save(userAuthority);
        return user;
    }

    public List<User> getUsersWithAuthority(String authority) {

//        Iterable<UserAuthority> allList=userAuthorityRepository.findAll();
//        List<Optional<User>> result=new ArrayList<>();
//        for(var user: allList){
//            if(user.getRole().equals(authority)){
//                result.add(userRepository.findById(user.getUserId()));
//            }
//        }

        return userRepository.getUsersWithAuthority(authority);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
