package com.ibm.patient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private Long patientId;

	@ManyToOne
	@JoinColumn(name = "insurance_id", nullable = true)
	private Insurance insurance;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "contact", nullable = false)
	private String contact;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	public Patient() {
		super();
	}

	public Patient(Long patientId, Insurance insurance, String name, String dateOfBirth, String gender, String contact,
			String email, String password) {
		super();
		this.patientId = patientId;
		this.insurance = insurance;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contact = contact;
		this.email = email;
		this.password = password;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", name=" + name + ", gender=" + gender + ", contact=" + contact
				+ ", email=" + email + "]";
	}

}
