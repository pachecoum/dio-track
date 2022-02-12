package com.primeiro.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExceptionResponose")
public class ExceptionDTO {

	@JsonProperty("message")
	private String message;

	@JsonProperty("path")
	private String path;


    public ExceptionDTO(String message) {
		this.message = message;
	}

	public static ExceptionDTO fromEntity(RuntimeException ex) {
		return new ExceptionDTO(ex.getMessage());
	}

	/**
	 * Get message
	 *
	 * @return message
	 */
	@Schema(example = "Error message", description = "")

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	/**
	 * Get message
	 *
	 * @return message
	 */
	@Schema(example = "/path?param=query", description = "")

	public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
