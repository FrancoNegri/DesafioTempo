package com.example.demo.model.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "user")
@Table(name = "user_table")
public class User {

	@Id
	@NotNull
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}

	// TODO: not totally secured, but no time for salt
	public void hashPassword() {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		messageDigest.update(password.getBytes());
		password = new String(messageDigest.digest());
	}

}
