package com.ibm.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.doctor.model.Doctor;
import com.ibm.doctor.repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	public Optional<Doctor> getDoctorById(Long id) {
		return doctorRepository.findById(id);
	}

	public Optional<Doctor> getDoctorByEmail(String email) {
		return doctorRepository.findByEmail(email);
	}

	public Doctor createDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	public Doctor updateDoctor(Long id, Doctor updated) {
		return doctorRepository.findById(id).map(existing -> {
			existing.setName(updated.getName());
			existing.setGender(updated.getGender());
			existing.setSpecialization(updated.getSpecialization());
			existing.setContact(updated.getContact());
			existing.setEmail(updated.getEmail());
			existing.setPassword(updated.getPassword());
			return doctorRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
	}

	public void deleteDoctor(Long id) {
		doctorRepository.deleteById(id);
	}

}
