package com.ibm.patient.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "insurances")
public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "insurance_id")
	private Long insuranceId;

	@Column(name = "package_name", nullable = false)
	private String packageName;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "amount", nullable = false)
	private Double amount;

	public Insurance() {
		super();
	}

	public Insurance(Long insuranceId, String packageName, String type, Double amount) {
		super();
		this.insuranceId = insuranceId;
		this.packageName = packageName;
		this.type = type;
		this.amount = amount;
	}

	public Long getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(Long insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Insurance [insuranceId=" + insuranceId + ", packageName=" + packageName + ", type=" + type
				+ ", amount=" + amount + "]";
	}

}
