package com.ibm.doctor.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.doctor.model.Doctor;
import com.ibm.doctor.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/search")
	public ResponseEntity<Doctor> getDoctorByEmail(@RequestParam String email) {
		return doctorService.getDoctorByEmail(email)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctor));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
		try {
			return ResponseEntity.ok(doctorService.updateDoctor(id, doctor));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
		doctorService.deleteDoctor(id);
		return ResponseEntity.noContent().build();
	}

}
