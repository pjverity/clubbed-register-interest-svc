package uk.co.vhome.clubbed.services;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.vhome.clubbed.mail.builders.MessageBuilder;
import uk.co.vhome.clubbed.mail.services.EMailService;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@Validated
public class RegistrationController
{

	private static final String FROM_NAME = "Reigate Junior Joggers";

	private static final String PRIMARY_ADMIN_EMAIL_ADDRESS = "admin@reigatejuniorjoggers.co.uk";

	private static final String SECONDARY_ADMIN_EMAIL_ADDRESS = "administrator@reigatejuniorjoggers.co.uk";

	private static final String SUBJECT = "Thanks for registering your interest";

	private final EMailService eMailService;

	private static final String BODY = "<!DOCTYPE html>\n" +
			                                   "<html lang=\"en\">" +
			                                   "<head>" +
			                                   "<meta charset=\"UTF-8\">" +
			                                   "<style>" +
			                                   "body {font-family: sans-serif;}" +
			                                   "</style>" +
			                                   "</head>" +
			                                   "<body>" +
			                                   "<p>Hi,</p>" +
			                                   "<p>Thanks for registering your interest in Reigate Junior Joggers! Any updates will be sent to this address.</p>" +
			                                   "<p>If you no longer wish to receive updates from us, please just <a href=\"mailto:admin@reigatejuniorjoggers.co.uk (Reigate Junior Joggers)?subject=Unsubscribe\">drop us a mail</a> and we'll remove you from our list.</p>" +
			                                   "<p>We will be in touch very soon!</p>" +
			                                   "<p><strong>Reigate Junior Joggers</strong></p>" +
			                                   "</body>" +
			                                   "</html>";

	@Inject
	public RegistrationController(EMailService eMailService)
	{
		this.eMailService = eMailService;
	}

	@CrossOrigin(origins = {"http://localhost:63342", "http://www.reigatejuniorjoggers.co.uk"})
	@RequestMapping("/register")
	public String register(@RequestParam(value = "email") @Valid @NotBlank @Email String email)
	{
		return sendMail(email) ? "OK" : "ERROR";
	}

	private boolean sendMail(String toAddress)
	{
		MessageBuilder messageBuilder = new MessageBuilder();

		messageBuilder.addToAddress(toAddress)
				.setFromAddress(PRIMARY_ADMIN_EMAIL_ADDRESS, FROM_NAME)
				.addBccAddress(PRIMARY_ADMIN_EMAIL_ADDRESS)
				.addBccAddress(SECONDARY_ADMIN_EMAIL_ADDRESS)
				.setSubject(SUBJECT)
				.setMessage(BODY);

		try
		{
			eMailService.send(messageBuilder);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}