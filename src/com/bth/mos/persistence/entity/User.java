package com.bth.mos.persistence.entity;

public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Gender {
		M, F;

	}

	private String name;
	private int age;
	private Gender gender;
	private String skill;

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	@Override
	public void validate() {
		if (age <= 0) {
			throw new IllegalArgumentException("Invalid Age");
		}
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getSkill() {
		return skill;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", gender=" + gender + ", skill=" + skill + "]";
	}

}
