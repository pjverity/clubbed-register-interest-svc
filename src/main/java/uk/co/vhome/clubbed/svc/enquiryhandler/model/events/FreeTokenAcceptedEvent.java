package uk.co.vhome.clubbed.svc.enquiryhandler.model.events;

/**
 * Raised when a member or newly registered visitor has successfully claimed
 * their free token
 */
public class FreeTokenAcceptedEvent extends EnquiryEvent
{
	public FreeTokenAcceptedEvent(String emailAddress)
	{
		super(emailAddress);
	}
}
