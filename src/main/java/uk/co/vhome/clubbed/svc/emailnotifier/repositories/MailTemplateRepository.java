package uk.co.vhome.clubbed.svc.emailnotifier.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.vhome.clubbed.svc.emailnotifier.model.MailTemplate;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long>
{
	MailTemplate findByName(String name);
}
