package com.primeiro.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.primeiro.spring.dto.ExceptionDTO;
import com.primeiro.spring.exception.AddressNotFoundException;
import com.primeiro.spring.exception.NotImplementedException;
import com.primeiro.spring.exception.UserNotFoundException;
import com.primeiro.spring.exception.UsernameIsUsedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ControllerAdvice
public class ExceptionHandlerController{

	@ExceptionHandler({ UsernameIsUsedException.class })
	@ApiResponse(responseCode = "400", description = "Bad request", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDTO.class)) })
	public ResponseEntity<ExceptionDTO> handlerBadRequest(HttpServletRequest req, RuntimeException ex) {
		return createResponse(req, ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ AddressNotFoundException.class, UserNotFoundException.class })
	@ApiResponse(responseCode = "404", description = "Data not found", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDTO.class)) })
	public ResponseEntity<ExceptionDTO> handlerNotFound(HttpServletRequest req, RuntimeException ex) {
		return createResponse(req, ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ApiResponse(responseCode = "400", description = "Bad request", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDTO.class)) })
	public ResponseEntity<Map<String, String>> handleValidationExceptions(HttpServletRequest req,
	  MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		errors.put("path", (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));

		return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotImplementedException.class)
	@ApiResponse(responseCode = "501", description = "Route not implemented", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionDTO.class)) })
	public ResponseEntity<ExceptionDTO> handlerNotImplemented(HttpServletRequest req, RuntimeException ex) {
		return createResponse(req, ex, HttpStatus.NOT_IMPLEMENTED);
	}

	private ResponseEntity<ExceptionDTO> createResponse(HttpServletRequest req, RuntimeException ex,
			HttpStatus httpStatus) {
		ExceptionDTO exDto = ExceptionDTO.fromEntity(ex);
		exDto.setPath((String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		return new ResponseEntity<ExceptionDTO>(exDto, httpStatus);
	}



}
