package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import java.util.Objects;
import java.util.StringJoiner;

public class NewClubEnquiryCommand
{
	private final String domain;

	private final String emailAddress;

	private final String firstName;

	private final String lastName;

	public NewClubEnquiryCommand(String domain, String emailAddress, String firstName, String lastName)
	{
		this.domain = domain;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	String getDomain()
	{
		return domain;
	}

	String getEmailAddress()
	{
		return emailAddress;
	}

	String getFirstName()
	{
		return firstName;
	}

	String getLastName()
	{
		return lastName;
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NewClubEnquiryCommand that = (NewClubEnquiryCommand) o;

		return Objects.equals(this.domain, that.domain) &&
				       Objects.equals(this.emailAddress, that.emailAddress) &&
				       Objects.equals(this.firstName, that.firstName) &&
				       Objects.equals(this.lastName, that.lastName);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(domain, emailAddress, firstName, lastName);
	}

	@Override
	public String toString()
	{
		return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
				       .add("domain = " + domain)
				       .add("emailAddress = " + emailAddress)
				       .add("firstName = " + firstName)
				       .add("lastName = " + lastName)
				       .toString();
	}
}
