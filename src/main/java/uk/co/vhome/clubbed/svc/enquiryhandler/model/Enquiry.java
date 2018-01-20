package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.spring.stereotype.Aggregate;
import uk.co.vhome.clubbed.apiobjects.ClubEnquiryCreatedEvent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Entity(name = "enquiries")
public class Enquiry
{
	@Id
	@Column(name = "email_address")
	@AggregateIdentifier
	private String emailAddress;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "enquiry_time", nullable = false)
	private Instant enquiryTime;

	protected Enquiry()
	{
	}

	@CommandHandler
	Enquiry(NewClubEnquiryCommand newClubEnquiryCommand)
	{
		apply(new ClubEnquiryCreatedEvent(newClubEnquiryCommand.getEmailAddress(),
		                                  newClubEnquiryCommand.getFirstName(),
		                                  newClubEnquiryCommand.getLastName()));
	}

	@EventHandler
	void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent, @Timestamp Instant instant)
	{
		emailAddress = clubEnquiryCreatedEvent.getEmailAddress();
		firstName = clubEnquiryCreatedEvent.getFirstName();
		lastName = clubEnquiryCreatedEvent.getLastName();
		enquiryTime = instant;
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
}
