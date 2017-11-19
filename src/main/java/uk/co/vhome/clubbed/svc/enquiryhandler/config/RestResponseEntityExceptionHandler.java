package uk.co.vhome.clubbed.svc.enquiryhandler.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> constraintViolationExceptionHandler(Exception ex, WebRequest request)
	{
		return new ResponseEntity<>("Constraint Violation", new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}