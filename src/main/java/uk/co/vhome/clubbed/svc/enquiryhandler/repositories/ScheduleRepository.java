package uk.co.vhome.clubbed.svc.enquiryhandler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.Schedule;

import java.util.List;

@CrossOrigin(origins = "${enquiry-handler.web-host}")
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>
{
	@SuppressWarnings("unused")
	@RestResource(path = "activeSchedules", rel = "activeSchedules")
	List<Schedule> findAllByActiveIsTrueOrderByCommencesAscTimeAsc();
}
