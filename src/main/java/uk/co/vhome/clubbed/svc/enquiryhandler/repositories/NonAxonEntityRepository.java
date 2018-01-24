package uk.co.vhome.clubbed.svc.enquiryhandler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.Enquiry;

public interface NonAxonEntityRepository extends JpaRepository<Enquiry, String>
{
}
