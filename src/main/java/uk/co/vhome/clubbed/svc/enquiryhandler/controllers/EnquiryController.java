package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.NewClubEnquiryCommand;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collections;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@RestController
@Validated
@RequestMapping("/v1/enquiries")
public class EnquiryController
{
	private static final Log LOGGER = LogFactory.getLog(EnquiryController.class);

	private final CommandBus commandBus;

	@Inject
	public EnquiryController(CommandBus commandBus)
	{
		this.commandBus = commandBus;
	}

	@CrossOrigin(origins = {"http://www.caterhamladiesjoggers.co.uk",
	                        "http://www.horshamladiesjoggers.co.uk",
	                        "http://www.oxtedladiesjoggers.co.uk",
	                        "http://www.reigatejuniorjoggers.co.uk"})
	@PostMapping(path = "/club-enquiry/emails/{email}")
	public DeferredResult<ResponseEntity<Object>> register(@PathVariable @Valid @NotBlank @Email String email,
	                                                       @Valid UserDetail userInfo)
	{

		DeferredResult<ResponseEntity<Object>> handlerResult = new DeferredResult<>();

		commandBus.dispatch(asCommandMessage(new NewClubEnquiryCommand(email, userInfo.getFirstName(), userInfo.getLastName())),
		                    new CommandCallback<>()
		                    {
			                    @Override
			                    public void onSuccess(CommandMessage<?> commandMessage, Object result)
			                    {
				                    LOGGER.info("Registered enquiry for: " + email);
				                    handlerResult.setResult(ResponseEntity.ok().build());
			                    }

			                    @Override
			                    public void onFailure(CommandMessage<?> commandMessage, Throwable cause)
			                    {
				                    ConstraintViolationException violationException = cause.getCause() instanceof ConstraintViolationException ? (ConstraintViolationException) cause.getCause() : null;

				                    if (violationException != null)
				                    {
					                    if ("enquiries_email_address_pkey".equals(violationException.getConstraintName()))
					                    {
						                    ApiError apiError = new ApiError("email", "Address already registered");
						                    ResponseEntity<Object> body = ResponseEntity.badRequest().body(Collections.singleton(apiError));
						                    handlerResult.setErrorResult(body);
					                    }
				                    }
				                    else
				                    {
					                    handlerResult.setErrorResult(ResponseEntity.badRequest().body(cause.getMessage()));
				                    }

				                    LOGGER.error("Failed to register enquiry", cause);
			                    }
		                    });

		return handlerResult;
	}

}