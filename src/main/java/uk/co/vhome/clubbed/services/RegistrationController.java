package uk.co.vhome.clubbed.services;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mail.MailException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.vhome.clubbed.core.services.EMailService;

import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@Validated
public class RegistrationController
{
	private static final String FROM_ADDRESS = "no-reply@reigatejuniorjoggers.co.uk";

	private static final String FROM_NAME = "Reigate Junior Joggers";

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

	private boolean sendMail(String email)
	{
		try
		{
			Stream<InternetAddress> toAddresses = Stream.of(new InternetAddress(email));

			Stream<InternetAddress> bccAddresses = Stream.of(new InternetAddress("admin@reigatejuniorjoggers.co.uk"),
			                                                 new InternetAddress("administrator@reigatejuniorjoggers.co.uk"));

			eMailService.send(toAddresses,
			                  bccAddresses,
			                  FROM_ADDRESS,
			                  FROM_NAME,
			                  "Thanks for registering your interest",
			                  BODY);
		}
		catch (MailException e)
		{
			System.err.println("Failed to send mail notification: " + e.getMessage());
			return false;
		}
		catch (AddressException e)
		{
			e.printStackTrace();
		}

		return true;
	}

}