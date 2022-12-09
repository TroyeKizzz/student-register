package com.tamk.studentregister.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamk.studentregister.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
  @Override
  ArrayList<Student> findAll();
  Optional<Student> findByEmail(String email);
}
