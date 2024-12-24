package com.society.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.dto.RequestUserCredentials;
import com.society.serviceImp.UserService;
import com.society.util.Helper;

import lombok.RequiredArgsConstructor;
 
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
 
	private final UserDetailsService userDetailsService;
 
	private final AuthenticationManager authenticationManager;
 
	//private final PasswordEncoder passwordEncoder;
 
	private final JwtUtil jwtUtil;
 
	@Autowired
	private UserService userService; 
 
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody RequestUserCredentials user) {
 		try {
			String hashedPassword = Helper.hashPassword(user.getPassword());
 
			User userDb = userService.getUserByUserName(user.getUsername());
 
//			authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), hashedPassword));
			if (!hashedPassword.equals(userDb.getPassword())) {
 	            throw new Exception("Invalid password");
	        }
			//String role = userDb.getAuthorities().toString();
			//role = role.substring(1, role.length() - 1);

			String jwtToken = jwtUtil.generateToken(userDb.getUserName(),userDb.getUserRole());
 			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK,
					ApiMessages.LOGGED_IN_SUCCESSFULLY, Map.of("JWT Token", jwtToken));
		} catch (Exception e) {
			return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.UNAUTHORIZED,
					ApiMessages.INVALID_CREDENTIALS_MESSAGE, null);
		}
	}
	@PostMapping("/logout")
	public ResponseEntity<Object> logoutUser(@RequestHeader("Authorization") String authorizationHeader) {
		// Check if the header is present and well-formed
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.BAD_REQUEST, ApiMessages.BAD_REQUEST_MESSAGE,
					null);
		}
		// Extract the JWT token (removing "Bearer " prefix)
		String jwtToken = authorizationHeader.substring(7);
		// Add the token to the blacklist
		jwtUtil.blacklistToken(jwtToken);
		// Respond with success message
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.LOGGED_OUT_SUCCESSFULLY,
				null);
	}
 
}