package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.form.DutyForm;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.repository.DutyRepository;
import pl.polsl.student.barberio.repository.UserAuthorityRepository;


@Service
public class DutyService {
    private DutyRepository dutyRepository;

    private UserAuthorityRepository userAuthorityRepository;

    public Duty createDuty(DutyForm form) {
        var duty = new Duty();
        duty.setName(form.getName());
        duty.setDuration(form.getDuration());
        duty.setPrice(form.getPrice());
        duty.setDoneBy(form.getDoneBy());
        duty = this.dutyRepository.save(duty);

        return duty;
    }


    @Autowired
    public void setDutyRepository(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

}
