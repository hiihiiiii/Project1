package com.utc.dormitory_managing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.utc.dormitory_managing.dto.LoginRequest;
import com.utc.dormitory_managing.dto.ResponseDTO;
import com.utc.dormitory_managing.entity.User;
import com.utc.dormitory_managing.repository.UserRepo;
import com.utc.dormitory_managing.security.jwt.JwtUtils;

public interface AuthService {
	ResponseDTO<String> signin(LoginRequest loginRequest, User user);

	ResponseDTO<String> handleRefreshToken(String refreshToken_in);

}

@Service
class AuthServiceImpl implements AuthService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils tokenProvider;

	@Override
	public ResponseDTO<String> signin(LoginRequest loginRequest, User user) {
		try {
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			// Set thông tin authentication vào Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.err.println("securitycontext");
			String accessToken = tokenProvider.generateAccessToken(authentication);
			System.err.println(accessToken);
			String refreshToken = tokenProvider.generateRefreshToken(authentication);

//			System.err.println(user.getAccessToken());
			
			user.setAccessToken(accessToken);
			user.setRefreshToken(refreshToken);

			userRepo.save(user);

			return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).accessToken(accessToken)
					.refreshToken(refreshToken).build();

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

	@Override
	public ResponseDTO<String> handleRefreshToken(String refreshToken_in) {
		try {

			String user_id = tokenProvider.getUserNameFromJwtToken(refreshToken_in);
			System.out.println("user_id: " + user_id);

			User user = userRepo.findByUserId(user_id).get();

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			String accessToken = tokenProvider.generateAccessToken(authentication);
			String refreshToken = tokenProvider.generateRefreshToken(authentication);

			user.setAccessToken(accessToken);
			user.setRefreshToken(refreshToken);

			return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).accessToken(accessToken)
					.refreshToken(refreshToken).build();

		} catch (ResourceAccessException e) {
			throw Problem.builder().withStatus(Status.EXPECTATION_FAILED).withDetail("ResourceAccessException").build();
		} catch (HttpServerErrorException | HttpClientErrorException e) {
			throw Problem.builder().withStatus(Status.SERVICE_UNAVAILABLE).withDetail("SERVICE_UNAVAILABLE").build();
		}
	}

}

