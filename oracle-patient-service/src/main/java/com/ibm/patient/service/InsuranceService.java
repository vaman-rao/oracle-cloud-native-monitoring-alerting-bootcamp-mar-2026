package com.ibm.patient.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.patient.model.Insurance;
import com.ibm.patient.repository.InsuranceRepository;

@Service
public class InsuranceService {

	@Autowired
	private InsuranceRepository insuranceRepository;

	public List<Insurance> getAllInsurances() {
		return insuranceRepository.findAll();
	}

	public Optional<Insurance> getInsuranceById(Long id) {
		return insuranceRepository.findById(id);
	}

	public Insurance createInsurance(Insurance insurance) {
		return insuranceRepository.save(insurance);
	}

	public Insurance updateInsurance(Long id, Insurance updated) {
		return insuranceRepository.findById(id).map(existing -> {
			existing.setPackageName(updated.getPackageName());
			existing.setType(updated.getType());
			existing.setAmount(updated.getAmount());
			return insuranceRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Insurance not found with id: " + id));
	}

	public void deleteInsurance(Long id) {
		insuranceRepository.deleteById(id);
	}

}
