package com.alliants.model.model;



public class Contact {

	private String id, firstName, lastName, address, phone_number,email, createdAt,updatedAt;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNumber() {
		return phone_number;
	}
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getcreatedAt() {
		return createdAt;
	}
	public void setcreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public String getupdatedAt() {
		return updatedAt;
	}
	public void setupdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}
