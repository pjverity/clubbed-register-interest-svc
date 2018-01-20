package uk.co.vhome.clubbed.svc.enquiryhandler;

import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.Enquiry;

@SpringBootApplication(scanBasePackages = "uk.co.vhome.clubbed")
public class ClubbedSvcEnquiryHandlerApplication
{
	private static final Class<ClubbedSvcEnquiryHandlerApplication> APPLICATION_CLASS = ClubbedSvcEnquiryHandlerApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(APPLICATION_CLASS, args);
	}

	@Bean
	public EventStorageEngine eventStorageEngine()
	{
		return new InMemoryEventStorageEngine();
	}

	@Bean
	public GenericJpaRepository<Enquiry> enquiryRepository(EntityManagerProvider entityManagerProvider, EventBus eventBus)
	{
		return new GenericJpaRepository<>(entityManagerProvider, Enquiry.class, eventBus);
	}

	@Bean
	SagaStore sagaStore()
	{
		return new InMemorySagaStore();
	}
}
