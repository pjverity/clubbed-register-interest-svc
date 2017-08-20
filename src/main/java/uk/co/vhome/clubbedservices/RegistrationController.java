package uk.co.vhome.clubbedservices;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

@RestController
@Validated
public class RegistrationController
{
	private static final String FROM_ADDRESS = "no-reply@reigatejuniorjoggers.co.uk";

	private static final String FROM_NAME = "Reigate Junior Joggers";

	private final JavaMailSender javaMailSender;

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
	public RegistrationController(JavaMailSender javaMailSender)
	{
		this.javaMailSender = javaMailSender;
	}

	@CrossOrigin(origins = {"http://localhost:63342"})
	@RequestMapping("/rjj/register")
	public String register(@RequestParam(value = "email") @Valid @NotBlank @Email String email)
	{
		return sendMail(email) ? "OK" : "ERROR";
	}

	private boolean sendMail(String email)
	{
		try
		{
			javaMailSender.send(mimeMessage ->
			                    {
				                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				                    message.setFrom(new InternetAddress(FROM_ADDRESS, FROM_NAME));

				                    try
				                    {
					                    message.addTo(email);

					                    message.addBcc("admin@reigatejuniorjoggers.co.uk");
					                    message.addBcc("administrator@reigatejuniorjoggers.co.uk");
				                    }
				                    catch (MessagingException e)
				                    {
					                    System.err.println("Failed to add recipient: " + e.getMessage());
				                    }

				                    message.setSubject("Thanks for registering your interest");
				                    message.setText(BODY, true);
			                    });
		}
		catch (MailException e)
		{
			System.err.println("Failed to send mail notification: " + e.getMessage());
			return false;
		}

		return true;
	}

}