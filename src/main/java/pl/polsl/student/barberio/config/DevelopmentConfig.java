package pl.polsl.student.barberio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.polsl.student.barberio.model.Appointment;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.model.User;
import pl.polsl.student.barberio.model.UserAuthority;
import pl.polsl.student.barberio.repository.AppointmentRepository;
import pl.polsl.student.barberio.repository.DutyRepository;
import pl.polsl.student.barberio.repository.UserAuthorityRepository;
import pl.polsl.student.barberio.repository.UserRepository;
import pl.polsl.student.barberio.service.UserService;

import java.util.Calendar;
import java.util.Date;

@Configuration
@Profile("devel")
public class DevelopmentConfig {
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;

    private AppointmentRepository appointmentRepository;
    private DutyRepository dutyRepository;

    private UserService userService;

    @Bean
    public void addTestUsers() {

        var encodedPassword = userService.getPasswordEncoder().encode("password");

        User user = new User();
        user.setFirstName("Tom");
        user.setLastName("Lee");
        user.setPassword(encodedPassword);
        user.setEmail("employee@mail.com");
        userRepository.save(user);
        UserAuthority userAuthorityEmployee = new UserAuthority();
        userAuthorityEmployee.setRole("EMPLOYEE");
        userAuthorityEmployee.setUserId(user.getId());
        userAuthorityRepository.save(userAuthorityEmployee);


        User user2 = new User();
        user2.setFirstName("Mike");
        user2.setLastName("Wazowsky");
        user2.setPassword(encodedPassword);
        user2.setEmail("client@mail.com");
        userRepository.save(user2);
        UserAuthority userAuthorityClient = new UserAuthority();
        userAuthorityClient.setRole("CLIENT");
        userAuthorityClient.setUserId(user2.getId());
        userAuthorityRepository.save(userAuthorityClient);

        User client2 = new User();
        client2.setEmail("client2@mail.com");
        client2.setPassword(encodedPassword);
        client2.setFirstName("Client");
        client2.setLastName("Number2");
        client2.setPhoneNumber("123321123");
        userRepository.save(client2);
        UserAuthority userAuthorityClient2=new UserAuthority();
        userAuthorityClient2.setRole("CLIENT");
        userAuthorityClient2.setUserId(client2.getId());
        userAuthorityRepository.save(userAuthorityClient2);


        User user3 = new User();
        user3.setFirstName("Janusz");
        user3.setLastName("Kowalski");
        user3.setPassword(encodedPassword);
        user3.setEmail("admin@mail.com");
        userRepository.save(user3);
        UserAuthority userAuthorityAdmin = new UserAuthority();
        userAuthorityAdmin.setRole("ADMIN");
        userAuthorityAdmin.setUserId(user3.getId());
        userAuthorityRepository.save(userAuthorityAdmin);

        Duty duty1 = new Duty();
        duty1.setName("haircut");
        duty1.setPrice(30);
        duty1.setDuration(30);
        duty1.getDoneBy().add(user);
        dutyRepository.save(duty1);


        Appointment appointment1 = new Appointment();
        appointment1.setDate(new Date(2023-1900, Calendar.JANUARY,20,14,30));
        appointment1.setCustomer(user2);
        appointment1.setEmployee(user);
        appointment1.setDuty(duty1);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDate(new Date(2023-1900, Calendar.JANUARY,22,14,30));
        appointment2.setCustomer(client2);
        appointment2.setEmployee(user);
        appointment2.setDuty(duty1);
        appointmentRepository.save(appointment2);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Autowired
    public void setUserService(UserService userService){this.userService=userService;}

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository){
        this.appointmentRepository=appointmentRepository;}

    @Autowired
    public void setDutyRepository(DutyRepository dutyRepository){this.dutyRepository=dutyRepository;}
}
