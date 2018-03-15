package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.AcceptFreeTokenCommand;
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

	@GetMapping(path = "/token-claim/emails/{email}")
	public DeferredResult<ResponseEntity<String>> test(@RequestHeader(HttpHeaders.HOST) String host,
	                                   @PathVariable @Valid @NotBlank @Email String email)
	{

		LOGGER.info(email + " accepting free token");

		AcceptFreeTokenCommand acceptFreeTokenCommand = new AcceptFreeTokenCommand(email);

		DeferredResult<ResponseEntity<String>> handlerResult = new DeferredResult<>();

		commandBus.dispatch(asCommandMessage(acceptFreeTokenCommand), tokenClaimCommandCallback(host, email, handlerResult));

		return handlerResult;
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

	private CommandCallback<Object, Object> tokenClaimCommandCallback(@RequestHeader(HttpHeaders.HOST) String host, @Valid @NotBlank @Email @PathVariable String email, DeferredResult<ResponseEntity<String>> handlerResult)
	{
		return new CommandCallback<>()
		{
			@Override
			public void onSuccess(CommandMessage<?> commandMessage, Object result)
			{
				handlerResult.setResult(ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, "http://"+host+"/token-claim-ok.html").build());
				LOGGER.info( "Successfully claimed token for " + email);
			}

			@Override
			public void onFailure(CommandMessage<?> commandMessage, Throwable cause)
			{
				handlerResult.setErrorResult(ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, "http://"+host+"/token-claim-failed.html").build());
				LOGGER.error( "Unsuccessfully claimed token for " + email, cause);
			}
		};
	}

	private CommandCallback<Object, Object> newEnquiryCommandCallback(String email, DeferredResult<ResponseEntity<Object>> handlerResult)
	{
		return new CommandCallback<>()
		{
			@Override
			public void onSuccess(CommandMessage<?> commandMessage, Object result)
			{
				handlerResult.setResult(ResponseEntity.ok().build());
				LOGGER.info("Registered enquiry for: " + email);
			}

			@Override
			public void onFailure(CommandMessage<?> commandMessage, Throwable cause)
			{
				handlerResult.setErrorResult(ResponseEntity.badRequest().body(cause.getMessage()));
				LOGGER.error("Failed to register enquiry", cause);
			}
		};
	}

}