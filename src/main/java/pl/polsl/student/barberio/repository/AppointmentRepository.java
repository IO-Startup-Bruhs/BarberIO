package pl.polsl.student.barberio.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.student.barberio.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
