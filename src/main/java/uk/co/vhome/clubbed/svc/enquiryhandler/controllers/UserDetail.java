package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDetail
{
	@Size(min = 2, max = 32, message = "First name must be between {min} and {max} characters")
	@Pattern(regexp = "[a-zA-Z]+", message = "First name must contain only alphabetic characters")
	private String firstName;

	@Size(min = 2, max = 64, message = "Last name must be between {min} and {max} characters")
	@Pattern(regexp = "[a-zA-Z-]+", message = "Last name must contain only alphabetic characters or a hyphen")
	private String lastName;

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
}
