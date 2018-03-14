package uk.co.vhome.clubbed.svc.enquiryhandler.model.commands;

import java.util.Objects;
import java.util.StringJoiner;

public class NewClubEnquiryCommand
{
	/**
	 * The identity of the command is the e-mail address, it is a violation to have two commands with the same email
	 * address where the other fields differ.
	 */
	private final String emailAddress;

	private final String firstName;

	private final String lastName;

	private final String phoneNumber;

	public NewClubEnquiryCommand(String emailAddress, String firstName, String lastName, String phoneNumber)
	{
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NewClubEnquiryCommand that = (NewClubEnquiryCommand) o;

		return Objects.equals(this.emailAddress, that.emailAddress);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(emailAddress);
	}

	@Override
	public String toString()
	{
		return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
				       .add("emailAddress = " + emailAddress)
				       .add("firstName = " + firstName)
				       .add("lastName = " + lastName)
				       .add("phoneNumber = " + phoneNumber)
				       .toString();
	}
}
