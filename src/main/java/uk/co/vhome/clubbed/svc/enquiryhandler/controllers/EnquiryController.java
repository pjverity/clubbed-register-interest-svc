package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.NewClubEnquiryCommand;
import uk.co.vhome.clubbed.svc.enquiryhandler.repositories.NonAxonEntityRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collections;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@RestController
@Validated
@RequestMapping("/enquiries/v2")
public class EnquiryController
{
	private static final Log LOGGER = LogFactory.getLog(EnquiryController.class);

	private final CommandBus commandBus;

	private final NonAxonEntityRepository enquiryRepository;

	@Inject
	public EnquiryController(CommandBus commandBus, NonAxonEntityRepository enquiryRepository)
	{
		this.commandBus = commandBus;
		this.enquiryRepository = enquiryRepository;
	}

	@PostMapping(path = "/club-enquiry/emails/{email}")
	public DeferredResult<ResponseEntity<Object>> register(@PathVariable @Valid @NotBlank @Email String email,
	                                                       @Valid UserDetail userInfo)
	{

		DeferredResult<ResponseEntity<Object>> handlerResult = new DeferredResult<>();

		if (enquiryRepository.existsById(email))
		{
			ApiError apiError = new ApiError("email", "Address already registered");
			ResponseEntity<Object> body = ResponseEntity.badRequest().body(Collections.singleton(apiError));
			handlerResult.setResult(body);
			return handlerResult;
		}

		String phoneNumberOrNull = userInfo.getPhone().isEmpty() ? null : userInfo.getPhone();

		NewClubEnquiryCommand newClubEnquiryCommand = new NewClubEnquiryCommand(email,
		                                                                        userInfo.getFirstName(),
		                                                                        userInfo.getLastName(),
		                                                                        phoneNumberOrNull);

		commandBus.dispatch(asCommandMessage(newClubEnquiryCommand), newEnquiryCommandCallback(email, handlerResult));

		return handlerResult;
	}

	private CommandCallback<Object, Object> newEnquiryCommandCallback(String email, DeferredResult<ResponseEntity<Object>> handlerResult)
	{
		return new CommandCallback<>()
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
				LOGGER.error("Failed to register enquiry", cause);
				handlerResult.setErrorResult(ResponseEntity.badRequest().body(cause.getMessage()));
			}
		};
	}

}