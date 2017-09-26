package org.dynami.orm;

import java.util.Date;

@IEntity
public class Employ {
	public Employ() {}
	
	public Employ(long id, String name, String surname, Date birthdate, boolean retired, double salary) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.retired = retired;
		this.salary = salary;
	}
	
	@IField(pk=true)
	private long id;
	
	@IField(nullable=false)
	private String name;
	
	@IField(name="surname", nullable=false)
	private String surname;
	
	@IField
	private Date birthdate;
	
	@IField
	private boolean retired;
	
	@IField
	private double salary;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employ [id=" + id + ", name=" + name + ", surname=" + surname + ", birthdate=" + birthdate
				+ ", retired=" + retired + ", salary=" + salary + "]";
	}
}
