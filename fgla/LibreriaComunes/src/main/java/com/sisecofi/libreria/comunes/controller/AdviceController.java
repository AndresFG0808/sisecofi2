package com.sisecofi.libreria.comunes.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sisecofi.libreria.comunes.dto.ErrorInfo;
import com.sisecofi.libreria.comunes.util.exception.ErrorSistemaException;
import com.sisecofi.libreria.comunes.util.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

/**
 * 
 * Clase para el manejo de errores con excepciones personalizadas
 * 
 * @author ayuso2104@gmail.com
 *
 */
public class AdviceController {

	private static final Logger LOG = LoggerFactory.getLogger(AdviceController.class);
	private static final String REQUEST = "Request: {}";
	private static final String PINTAR_LOG = "debug: {}";

	@Value("${DEBUG}")
	private boolean debug;

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorInfo handleNotFoundError(NotFoundException ex, final HttpServletRequest request) {
		List<String> msj = new ArrayList<>();
		msj.add(HttpStatus.NOT_FOUND.getReasonPhrase());
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", "", msj,
				request.getRequestURL().toString());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorInfo handleValidationExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		String errorMessage = "";
		Set<String> msj = new HashSet<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			errorMessage = error.getDefaultMessage();
			msj.add(fieldName + ": " + errorMessage);
		}
		LOG.error("MethodArgumentNotValidException", ex);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());

	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorInfo exceptionGeneral(HttpServletRequest req, Throwable ex) {
		LOG.error(REQUEST, req.getRequestURL(), ex);
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		LOG.error("Throwable", ex);
		Set<String> msj = new HashSet<>();
		msj.add(ex.getMessage());
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

	@ExceptionHandler(ErrorSistemaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorInfo exceptionErrorSistemaException(HttpServletRequest req, ErrorSistemaException ex) {
		LOG.error(REQUEST, req.getRequestURL(), ex);
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		Set<String> msj = new HashSet<>();
		msj.add(ex.getMessage());
		LOG.error("ErrorSistemaException", ex);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorInfo exceptionErrorSistemaException(HttpServletRequest req, UserNotFoundException ex) {
		LOG.error(REQUEST, req.getRequestURL(), ex);
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		List<String> msj = new ArrayList<>();
		msj.add(ex.getMessage());
		LOG.error("UserNotFoundException", ex);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	ErrorInfo exceptionAccessDeniedException(HttpServletRequest req, AccessDeniedException ex) {
		LOG.error(REQUEST, req.getRequestURL(), ex);
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		Set<String> msj = new HashSet<>();
		msj.add(ex.getMessage());
		LOG.error("AccessDeniedException", ex);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorInfo handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
		LOG.error(REQUEST, req.getRequestURL(), e);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		Set<String> msj = new HashSet<>();
		e.getConstraintViolations().forEach(data -> {
			msj.add(data.getPropertyPath() + ":" + data.getMessage());
		});
		LOG.error("AccessDeniedException", e);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorInfo handleTransactionSystemException(HttpServletRequest req, TransactionSystemException e) {
		Throwable cause = e.getRootCause();
		if (cause instanceof ConstraintViolationException causeC) {
			return handleConstraintViolationException(req, causeC);
		}
		LOG.error(REQUEST, req.getRequestURL(), e);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = "";
		LOG.error(PINTAR_LOG, debug);
		if (debug) {
			stacktrace = sw.toString();
		}
		Set<String> msj = new HashSet<>();
		msj.add(e.getMessage());
		LOG.error("TransactionSystemException", e);
		return new ErrorInfo(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "", stacktrace, new ArrayList<>(msj),
				req.getRequestURL().toString());
	}

}
