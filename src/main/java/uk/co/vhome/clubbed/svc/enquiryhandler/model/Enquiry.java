package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import uk.co.vhome.clubbed.apiobjects.ClubEnquiryCreatedEvent;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Enquiry
{
	@AggregateIdentifier
	private String emailAddress;

	public Enquiry()
	{
	}

	@CommandHandler
	Enquiry(NewClubEnquiryCommand newClubEnquiryCommand)
	{
		apply(new ClubEnquiryCreatedEvent(newClubEnquiryCommand.getDomain(),
		                                  newClubEnquiryCommand.getEmailAddress(),
		                                  newClubEnquiryCommand.getFirstName(),
		                                  newClubEnquiryCommand.getLastName()));
	}

	@EventHandler
	void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent)
	{
		emailAddress = clubEnquiryCreatedEvent.getEmailAddress();
	}
}
