package uk.co.vhome.clubbed.svc.enquiryhandler.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.co.vhome.clubbed.svc.enquiryhandler.controllers.ApiError;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		List<ApiError> apiErrors = ex.getFieldErrors().stream()
				                         .map(ApiError::new)
				                         .collect(Collectors.toList());

		return handleExceptionInternal(ex, apiErrors, headers, status, request);
	}

}