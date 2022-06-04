package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(path = "/login")
	public SecretToken login(@RequestBody User user) {
		logger.info("Logging attepmt from user: " + user);
		user.hashPassword();
		Optional<User> exisntingUser = userRepository.findOne(Example.of(user, ExampleMatcher.matching().withIgnorePaths("id")));
		if (!exisntingUser.isPresent()) {
			throw new RuntimeException("User and password did not match");
		}
		String token = getJWTToken(user.getUsername());
		SecretToken secretToken = new SecretToken(exisntingUser.get().getId(), token);
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

	private final Logger logger = LoggerFactory.getLogger(ApplicationUser.class);

}
