package com.assigment.suretime.securityJwt.application.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
