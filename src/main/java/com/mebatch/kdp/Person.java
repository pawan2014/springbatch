package com.mebatch.kdp;

public class Person {

	private Integer person_ID;
	private String name;
	private String first;
	private String last;
	private String middle;
	private String email;
	private String phone;

	private String fax;
	private String title;

	public Person() {
	}

	public Integer getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(Integer person_ID) {
		this.person_ID = person_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Person [person_ID=" + person_ID + ", name=" + name + ", first=" + first + ", last=" + last + "]";
	}

}