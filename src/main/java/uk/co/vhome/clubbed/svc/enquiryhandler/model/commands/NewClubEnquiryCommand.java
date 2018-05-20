package uk.co.vhome.clubbed.svc.enquiryhandler.model.commands;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Issued when a new visitor to the site registers
 */
public class NewClubEnquiryCommand
{
	/**
	 * The identity of the command is the e-mail address, it is a violation to have two commands with the same email
	 * address where the other fields differ.
	 */
	private final String enquiryId;

	private final String firstName;

	private final String lastName;

	private final String phoneNumber;

	public NewClubEnquiryCommand(String enquiryId, String firstName, String lastName, String phoneNumber)
	{
		this.enquiryId = enquiryId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public String getEnquiryId()
	{
		return enquiryId;
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

		return Objects.equals(this.enquiryId, that.enquiryId);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(enquiryId);
	}

	@Override
	public String toString()
	{
		return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
				       .add("enquiryId = " + enquiryId)
				       .add("firstName = " + firstName)
				       .add("lastName = " + lastName)
				       .add("phoneNumber = " + phoneNumber)
				       .toString();
	}
}
