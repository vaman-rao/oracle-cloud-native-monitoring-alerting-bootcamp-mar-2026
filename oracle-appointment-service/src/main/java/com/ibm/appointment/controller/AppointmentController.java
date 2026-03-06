package com.ibm.appointment.controller;

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

import com.ibm.appointment.model.Appointment;
import com.ibm.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		return ResponseEntity.ok(appointmentService.getAllAppointments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		return appointmentService.getAppointmentById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<Appointment>> getByPatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
	}

	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<Appointment>> getByDoctor(@PathVariable Long doctorId) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId));
	}

	@PostMapping
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointment));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
		try {
			return ResponseEntity.ok(appointmentService.updateAppointment(id, appointment));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
		appointmentService.deleteAppointment(id);
		return ResponseEntity.noContent().build();
	}

}
