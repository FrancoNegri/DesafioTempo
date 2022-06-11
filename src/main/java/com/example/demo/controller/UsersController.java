package com.example.demo.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.user.LogoutUserResponse;
import com.example.demo.model.user.SecretToken;
import com.example.demo.model.user.User;
import com.example.demo.repos.UserRepository;
import com.example.demo.resources.Resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UsersController {

	@Autowired
	private UserRepository userRepository;
	// Better way: make set impl concurrent
	private static Set<String> validTokens = new HashSet<>();

	@PostMapping(path = "/login")
	public SecretToken login(@Valid @RequestBody User user) {
		logger.info("Logging attepmt from user: " + user);
		validateUser(user);
		String token = getJWTToken(user.getUsername());
		saveToken(token);
		SecretToken secretToken = new SecretToken(token);
		return secretToken;
	}

	private String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, Resources.SIGNING_KEY.getBytes()).compact();
		return token;
	}

	@PostMapping(path = "/close")
	public LogoutUserResponse close(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody User user) {
		logger.info("Logout for: " + user);
		validateUser(user);
		removeToken(token);
		LogoutUserResponse response = new LogoutUserResponse();
		response.setResponse("OK!");
		return response;
	}

	private void validateUser(User user) {
		user.hashPassword();
		Optional<User> existingUser = userRepository.findOne(Example.of(user, ExampleMatcher.matching()));
		if (!existingUser.isPresent()) {
			throw new UsernameNotFoundException(user.getUsername());
		}
	}

	private final Logger logger = LoggerFactory.getLogger(ApplicationUser.class);

	private static synchronized void saveToken(String saveToken) {
		validTokens.add(saveToken);
	}

	private static synchronized void removeToken(String saveToken) {
		validTokens.remove(saveToken);
	}

	public static synchronized boolean tokenIsPresent(String saveToken) {
		return validTokens.contains(saveToken);
	}

}
