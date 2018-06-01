package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import uk.co.vhome.clubbed.svc.security.MD5Helper;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.AcceptFreeTokenCommand;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.NewClubEnquiryCommand;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.events.ClubEnquiryCreatedEvent;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.events.FreeTokenAcceptedEvent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Aggregate root that represents a visitor making an enquiry on the site
 */
@Aggregate
@Entity(name = "enquiries")
public class Enquiry
{
	@Id
	@Column(name = "enquiry_id", nullable = false)
	private String enquiryId;

	@Column(name = "enquiry_time", nullable = false)
	private Instant enquiryTime;

	@Column(name = "token_accepted")
	private Boolean tokenAccepted;

	private Enquiry() { } // JPA

	@CommandHandler
	Enquiry(NewClubEnquiryCommand newClubEnquiryCommand)
	{
		apply(new ClubEnquiryCreatedEvent(newClubEnquiryCommand.getEmailAddress(),
		                                  newClubEnquiryCommand.getFirstName(),
		                                  newClubEnquiryCommand.getLastName(),
		                                  newClubEnquiryCommand.getPhoneNumber()));
	}

	@CommandHandler
	void handle(AcceptFreeTokenCommand acceptFreeTokenCommand)
	{
		if ( !isTokenAccepted() )
		{
			apply(new FreeTokenAcceptedEvent(acceptFreeTokenCommand.getEmailAddress()));
		}
	}

	@EventSourcingHandler
	private void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent, @Timestamp Instant enquiryTime)
	{
		String enquiryId = MD5Helper.hash(clubEnquiryCreatedEvent.getEmailAddress());

		setEnquiryId(enquiryId);
		setTokenAccepted(false);
		setEnquiryTime(enquiryTime);
	}

	@EventSourcingHandler
	private void on(FreeTokenAcceptedEvent clubEnquiryCreatedEvent)
	{
		setTokenAccepted(true);
	}

	public String getEnquiryId()
	{
		return enquiryId;
	}

	private void setEnquiryId(String enquiryId)
	{
		this.enquiryId = enquiryId;
	}

	public Instant getEnquiryTime()
	{
		return enquiryTime;
	}

	private void setEnquiryTime(Instant enquiryTime)
	{
		this.enquiryTime = enquiryTime;
	}

	public Boolean isTokenAccepted()
	{
		return tokenAccepted;
	}

	private void setTokenAccepted(Boolean tokenAccepted)
	{
		this.tokenAccepted = tokenAccepted;
	}
}
