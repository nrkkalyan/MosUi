package com.bth.mos.persistence.entity;
/**
 * Admin.java code declares two strings username and password
 */


public class Admin extends BaseEntity {

	
	private static final long serialVersionUID = 652561170569818739L;
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

        /**
         * This method we call for the validating whether username and password are filled or not
         * It help for the authentication of the Admin who are usint this tool
         */
	@Override
	public void validate() {

		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("username is required");
		}

		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("password is required");
		}
	}

}
