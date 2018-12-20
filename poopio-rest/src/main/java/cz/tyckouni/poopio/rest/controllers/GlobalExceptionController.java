package cz.tyckouni.poopio.rest.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import cz.tyckouni.poopio.rest.ApiError;
import cz.tyckouni.poopio.rest.exceptions.InvalidParameterException;
import cz.tyckouni.poopio.rest.exceptions.NotAuthorizedException;
import cz.tyckouni.poopio.rest.exceptions.PrivilegeException;
import cz.tyckouni.poopio.rest.exceptions.ResourceAlreadyExistingException;
import cz.tyckouni.poopio.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

/**
 * Class handling the Rest exceptions, translating them to HttpStatuses
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ApiError handleException(ResourceAlreadyExistingException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(InvalidParameterException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiError handleException(ResourceNotFoundException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(IllegalArgumentException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(InvalidFormatException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ApiError handleException(NotAuthorizedException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ApiError handleException(PrivilegeException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(MethodArgumentTypeMismatchException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiError handleException(Exception e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}
}
