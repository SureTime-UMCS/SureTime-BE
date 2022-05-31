package com.assigment.suretime.securityJwt.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse {
	private String token;
	private final String type = "Bearer";
	private String id;
	private String username;
	private String email;
	private List<String> roles;

}