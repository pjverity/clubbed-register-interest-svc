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

@RestController
@Validated
public class RegistrationController
{
	private static final Logger LOGGER = LogManager.getLogger();

	private final NotificationService notificationService;

	@Inject
	public RegistrationController(NotificationService notificationService)
	{
		this.notificationService = notificationService;
	}

	@CrossOrigin(origins = {"http://localhost:8080",
	                        "http://www.reigatejuniorjoggers.co.uk",
	                        "http://www.caterhamladiesjoggers.co.uk",
	                        "http://www.horshamladiesjoggers.co.uk"})
	@RequestMapping(path = "/v1/siteIds/{siteId}/registrations/emails/{email}", method = RequestMethod.POST)
	public String register(@PathVariable(value = "siteId") @Valid @NotBlank String siteId,
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