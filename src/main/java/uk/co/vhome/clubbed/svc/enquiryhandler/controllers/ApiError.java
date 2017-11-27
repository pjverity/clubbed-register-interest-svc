package uk.co.vhome.clubbed.svc.enquiryhandler.controllers;

import org.springframework.validation.FieldError;

public class ApiError
{
	private final String fieldName;

	private final String errorMessage;

	public ApiError(FieldError fieldError)
	{
		this(fieldError.getField(), fieldError.getDefaultMessage());
	}

	ApiError(String fieldName, String errorMessage)
	{
		this.fieldName = fieldName;
		this.errorMessage = errorMessage;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}
}
