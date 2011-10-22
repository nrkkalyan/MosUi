package com.bth.excel.video;

import java.io.Serializable;

import com.bth.mos.persistence.entity.User;

public class UserGradeDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private int grade = -1;

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "UserDetails [User=" + user + ", grade=" + grade + "]";
	}

}
