package uk.co.vhome.clubbed.svc.enquiryhandler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.Enquiry;

/**
 * Used to check if an {@link Enquiry} already exists. The naming purposely avoids clashing
 * with the {@code EntityRepository} bean that is an Axon Repository for persisting the {@code Enquiry} aggregate
 */
@RepositoryRestResource(exported = false)
public interface NonAxonEntityRepository extends JpaRepository<Enquiry, String>
{
}
