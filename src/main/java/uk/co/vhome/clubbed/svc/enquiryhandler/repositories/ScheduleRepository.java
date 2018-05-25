package uk.co.vhome.clubbed.svc.enquiryhandler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>
{
	@SuppressWarnings("unused")
	@RestResource(path = "activeSchedules", rel = "activeSchedules")
	List<Schedule> findAllByActiveIsTrueOrderByCommencesAscTimeAsc();
}
