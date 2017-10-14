package uk.co.vhome.clubbed.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.co.vhome.clubbed.notifications.NewInterestRegisteredNotification;
import uk.co.vhome.clubbed.notifications.services.NotificationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@Validated
public class RegistrationController
{
	private static final Logger LOGGER = LogManager.getLogger();

	private final NotificationService notificationService;

	private static final String VALID_SITE_IDS_PATTEN = "CLJ|HLJ|OLJ|RJJ";

	@Inject
	public RegistrationController(NotificationService notificationService)
	{
		this.notificationService = notificationService;
	}

	@CrossOrigin("*")
	@GetMapping
	public String welcome()
	{
		return "Interest Registration Service is running";
	}

	@CrossOrigin(origins = {"http://www.caterhamladiesjoggers.co.uk",
	                        "http://www.horshamladiesjoggers.co.uk",
	                        "http://www.oxtedladiesjoggers.co.uk",
	                        "http://www.reigatejuniorjoggers.co.uk"})
	@PostMapping(path = "/v1/siteIds/{siteId}/registrations/emails/{email}")
	public String register(@PathVariable(value = "siteId") @Valid @NotBlank @Pattern(regexp = VALID_SITE_IDS_PATTEN) String siteId,
	                       @PathVariable(value = "email") @Valid @NotBlank @Email String recipientEmailAddress)
	{

		try
		{
			notificationService.postNotification(new NewInterestRegisteredNotification(siteId, recipientEmailAddress));
			return "OK";
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to register email address", e);
			return "ERROR";
		}
	}

}