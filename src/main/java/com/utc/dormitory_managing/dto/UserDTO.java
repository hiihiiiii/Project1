package com.utc.dormitory_managing.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String userId;
	private String username;
	private String password;
	private Long expired;
	private String refreshToken;
	private String accessToken;
}
