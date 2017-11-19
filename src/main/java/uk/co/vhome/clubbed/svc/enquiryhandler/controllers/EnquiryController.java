package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.NewClubEnquiryCommand;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@RestController
@Validated
public class EnquiryController
{
	private static final Log LOGGER = LogFactory.getLog(EnquiryController.class);

	private final CommandBus commandBus;

	@Inject
	public EnquiryController(CommandBus commandBus)
	{
		this.commandBus = commandBus;
	}

	@CrossOrigin("*")
	@GetMapping
	public String getStatus()
	{
		return "OK";
	}

	@CrossOrigin(origins = {"localhost",
	                        "http://www.caterhamladiesjoggers.co.uk",
	                        "http://www.horshamladiesjoggers.co.uk",
	                        "http://www.oxtedladiesjoggers.co.uk",
	                        "http://www.reigatejuniorjoggers.co.uk"})
	@PostMapping(path = "/v1/enquiries/club-enquiry/emails/{email}")
	public String register(@PathVariable(value = "email") @Valid @NotBlank @Email String recipientEmailAddress,
	                       @RequestParam("firstName") String firstName,
	                       @RequestParam("lastName") String lastName)
	{

		try
		{
			String domain = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getHost();

			commandBus.dispatch(asCommandMessage(new NewClubEnquiryCommand(domain, recipientEmailAddress, firstName, lastName)));

			return "OK";
		}
		catch (Exception e)
		{
			LOGGER.error("Failed to register email address", e);
			return "ERROR";
		}
	}

}