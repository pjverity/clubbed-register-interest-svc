package uk.co.vhome.clubbed.svc.enquiryhandler.model.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Issued when a member or newly registered visitor claims a free token
 */
public class AcceptFreeTokenCommand
{
	@TargetAggregateIdentifier
	private final String enquiryId;

	private final String emailAddress;

	public AcceptFreeTokenCommand(String enquiryId, String emailAddress)
	{
		this.enquiryId = enquiryId;
		this.emailAddress = emailAddress;
	}

	public String getEnquiryId()
	{
		return enquiryId;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}
}
