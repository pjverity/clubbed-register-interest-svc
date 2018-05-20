package uk.co.vhome.clubbed.svc.enquiryhandler.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.MailTemplate;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long>
{
	MailTemplate findByName(String name);
}
