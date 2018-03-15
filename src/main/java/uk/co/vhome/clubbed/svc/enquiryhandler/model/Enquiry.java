package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import uk.co.vhome.clubbed.apiobjects.ClubEnquiryCreatedEvent;
import uk.co.vhome.clubbed.apiobjects.FreeTokenAcceptedEvent;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.AcceptFreeTokenCommand;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.NewClubEnquiryCommand;

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
	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "enquiry_time", nullable = false)
	private Instant enquiryTime;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "token_accepted")
	private Boolean tokenAccepted;

	protected Enquiry()
	{
	}

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
		apply(new FreeTokenAcceptedEvent(acceptFreeTokenCommand.getEmailAddress()));
	}

	@EventSourcingHandler
	private void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent, @Timestamp Instant enquiryTime)
	{
		setEmailAddress(clubEnquiryCreatedEvent.getEmailAddress());
		setFirstName(clubEnquiryCreatedEvent.getFirstName());
		setLastName(clubEnquiryCreatedEvent.getLastName());
		setPhoneNumber(clubEnquiryCreatedEvent.getPhoneNumber());
		setTokenAccepted(false);
		setEnquiryTime(enquiryTime);
	}

	@EventSourcingHandler
	private void on(FreeTokenAcceptedEvent clubEnquiryCreatedEvent)
	{
		setTokenAccepted(true);
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Instant getEnquiryTime()
	{
		return enquiryTime;
	}

	public void setEnquiryTime(Instant enquiryTime)
	{
		this.enquiryTime = enquiryTime;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Boolean getTokenAccepted()
	{
		return tokenAccepted;
	}

	public void setTokenAccepted(Boolean tokenAccepted)
	{
		this.tokenAccepted = tokenAccepted;
	}
}
