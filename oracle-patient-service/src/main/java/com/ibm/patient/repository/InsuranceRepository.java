package com.ibm.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.patient.model.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

}
