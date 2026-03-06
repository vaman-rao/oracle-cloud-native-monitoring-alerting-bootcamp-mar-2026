package com.ibm.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.patient.model.Insurance;
import com.ibm.patient.service.InsuranceService;

@RestController
@RequestMapping("/insurances")
public class InsuranceController {

	@Autowired
	private InsuranceService insuranceService;

	@GetMapping
	public ResponseEntity<List<Insurance>> getAll() {
		return ResponseEntity.ok(insuranceService.getAllInsurances());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Insurance> getById(@PathVariable Long id) {
		return insuranceService.getInsuranceById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Insurance> create(@RequestBody Insurance insurance) {
		return ResponseEntity.status(HttpStatus.CREATED).body(insuranceService.createInsurance(insurance));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Insurance> update(@PathVariable Long id, @RequestBody Insurance insurance) {
		try {
			return ResponseEntity.ok(insuranceService.updateInsurance(id, insurance));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		insuranceService.deleteInsurance(id);
		return ResponseEntity.noContent().build();
	}

}
