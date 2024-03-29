package pl.polsl.student.barberio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.polsl.student.barberio.model.*;
import pl.polsl.student.barberio.repository.*;
import pl.polsl.student.barberio.service.UserService;

import java.time.LocalDateTime;
import java.time.Month;

@Configuration
@Profile("devel")
public class DevelopmentConfig {
    private UserRepository userRepository;
    private UserAuthorityRepository userAuthorityRepository;

    private AppointmentRepository appointmentRepository;
    private DutyRepository dutyRepository;
    private WorkHoursRepository workHoursRepository;
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

        User userr = new User();
        userr.setFirstName("Tomas");
        userr.setLastName("Leemon");
        userr.setPassword(encodedPassword);
        userr.setEmail("employeee@mail.com");
        userRepository.save(userr);
        UserAuthority userAuthorityEmployeee = new UserAuthority();
        userAuthorityEmployeee.setRole("EMPLOYEE");
        userAuthorityEmployeee.setUserId(userr.getId());
        userAuthorityRepository.save(userAuthorityEmployeee);


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

        Duty duty2 = new Duty();
        duty2.setName("triming");
        duty2.setPrice(90);
        duty2.setDuration(90);
        duty2.getDoneBy().add(user);
        dutyRepository.save(duty2);


        Appointment appointment1 = new Appointment();
        appointment1.setDate(LocalDateTime.of(2023, Month.FEBRUARY,2,12,15));
        appointment1.setCustomer(user2);
        appointment1.setEmployee(user);
        appointment1.setDuty(duty1);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDate(LocalDateTime.of(2023, Month.FEBRUARY,2,14,30));
        appointment2.setCustomer(client2);
        appointment2.setEmployee(user);
        appointment2.setDuty(duty1);
        appointmentRepository.save(appointment2);

//        Appointment appointment3 = new Appointment();
//        appointment3.setDate(LocalDateTime.of(2023, Month.FEBRUARY,02,15,30));
//        appointment3.setCustomer(client2);
//        appointment3.setEmployee(user);
//        appointment3.setDuty(duty2);
//        appointmentRepository.save(appointment3);

        WorkHours workHours1 = new WorkHours();
        workHours1.setFinishHour(9);
        workHours1.setFinishHour(18);
        workHours1.setEmployee(user);
        workHoursRepository.save(workHours1);
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
    @Autowired
    public void setWorkHoursRepository(WorkHoursRepository workHoursRepository) {
        this.workHoursRepository = workHoursRepository;
    }

}
