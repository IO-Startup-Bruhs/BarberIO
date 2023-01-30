package pl.polsl.student.barberio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.student.barberio.model.WorkHours;
import pl.polsl.student.barberio.repository.WorkHoursRepository;

@Service
public class WorkHoursService {
    private WorkHoursRepository workHoursRepository;
    public WorkHours getEmployeeWorkHours(long employeeId){
        WorkHours employeeWorkHours = new WorkHours();
        for(WorkHours workHours: workHoursRepository.findAll()){
            if(workHours.getEmployee().getId() == employeeId){
                employeeWorkHours = workHours;
            }
        }
        return employeeWorkHours;
    }

    @Autowired
    public void setWorkHoursRepository(WorkHoursRepository workHoursRepository) {
        this.workHoursRepository = workHoursRepository;
    }
}
