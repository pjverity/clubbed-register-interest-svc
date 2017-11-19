package uk.co.vhome.clubbed.svc.enquiryhandler;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "uk.co.vhome.clubbed")
public class ClubbedSvcEnquiryHandlerApplication extends SpringBootServletInitializer
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
}
