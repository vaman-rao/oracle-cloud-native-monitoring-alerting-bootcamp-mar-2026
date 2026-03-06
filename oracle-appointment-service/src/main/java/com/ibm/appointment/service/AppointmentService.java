package com.ibm.appointment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.appointment.model.Appointment;
import com.ibm.appointment.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public List<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}

	public Optional<Appointment> getAppointmentById(Long id) {
		return appointmentRepository.findById(id);
	}

	public List<Appointment> getAppointmentsByPatientId(Long patientId) {
		return appointmentRepository.findByPatientId(patientId);
	}

	public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
		return appointmentRepository.findByDoctorId(doctorId);
	}

	public Appointment createAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	public Appointment updateAppointment(Long id, Appointment updated) {
		return appointmentRepository.findById(id).map(existing -> {
			existing.setDoctorId(updated.getDoctorId());
			existing.setPatientId(updated.getPatientId());
			existing.setDate(updated.getDate());
			existing.setSlot(updated.getSlot());
			existing.setStatus(updated.getStatus());
			return appointmentRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
	}

	public void deleteAppointment(Long id) {
		appointmentRepository.deleteById(id);
	}

}
