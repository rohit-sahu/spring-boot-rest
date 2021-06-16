package io.devzona.springboot.emailproducer.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
			final HttpServletRequest request) {
		final ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(ExceptionUtils.getStackTrace(exception));
		error.callerURL(request.getRequestURI());
		log.error(ExceptionUtils.getStackTrace(exception));
		return error;
	}

	@ExceptionHandler(EmailProducerException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleMbException(final EmailProducerException exception,
															  final HttpServletRequest request) {
		final ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(ExceptionUtils.getStackTrace(exception));
		error.callerURL(request.getRequestURI());
		log.error(ExceptionUtils.getStackTrace(exception));
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleException(final Exception exception, final HttpServletRequest request) {
		final ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.callerURL(request.getRequestURI());
		return error;
	}
}
