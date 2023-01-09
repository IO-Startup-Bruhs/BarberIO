package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.Duty;
import pl.polsl.student.barberio.repository.DutyRepository;

import java.util.List;

@Service
public class DutyService {
    private DutyRepository dutyRepository;

    @Autowired
    public void setDutyRepository(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }
}
