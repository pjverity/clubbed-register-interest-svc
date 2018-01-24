package uk.co.vhome.clubbed.svc.enquiryhandler.model.commands;

import java.util.Objects;
import java.util.StringJoiner;

public class NewClubEnquiryCommand
{
	private final String emailAddress;

	private final String firstName;

	private final String lastName;

	public NewClubEnquiryCommand(String emailAddress, String firstName, String lastName)
	{
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NewClubEnquiryCommand that = (NewClubEnquiryCommand) o;

		return Objects.equals(this.emailAddress, that.emailAddress) &&
				       Objects.equals(this.firstName, that.firstName) &&
				       Objects.equals(this.lastName, that.lastName);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(emailAddress, firstName, lastName);
	}

	@Override
	public String toString()
	{
		return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
				       .add("emailAddress = " + emailAddress)
				       .add("firstName = " + firstName)
				       .add("lastName = " + lastName)
				       .toString();
	}
}
