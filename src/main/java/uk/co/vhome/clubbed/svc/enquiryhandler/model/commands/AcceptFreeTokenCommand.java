package uk.co.vhome.clubbed.svc.enquiryhandler.model.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Issued when a member or newly registered visitor claims a free token
 */
public class AcceptFreeTokenCommand
{
	@TargetAggregateIdentifier
	private final String emailAddress;

	public AcceptFreeTokenCommand(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}
}
