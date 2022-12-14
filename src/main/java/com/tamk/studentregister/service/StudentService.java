package com.tamk.studentregister.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamk.studentregister.model.Student;
import com.tamk.studentregister.repository.StudentRepository;

@Service
public class StudentService {
  @Autowired
  StudentRepository studentRepository;

  public Optional<Student> findStudentById(Long id) {
    return studentRepository.findById(id);
  }

  public Optional<Student> findStudentByEmail(String email) {
    return studentRepository.findByEmail(email);
  }

  public ArrayList<Student> findAll() {
    return studentRepository.findAll();
  }

  public Optional<Student> createStudent(Student student) {
    Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
    Optional<Student> newStudent = Optional.empty();
    if (existing.isEmpty()) {
      newStudent = Optional.of(studentRepository.save(new Student(
        student.getFirstName(), 
        student.getLastName(), 
        student.getEmail()
      )));
    }
    return newStudent;
  }

  public boolean edit(Long studentId, Student editedStudent) {
    try {
      Optional<Student> searchResult = studentRepository.findById(studentId);
      if (searchResult.isPresent()) {
        Student student = searchResult.get();
        student.setFirstName(editedStudent.getFirstName());
        student.setLastName(editedStudent.getLastName());
        student.setEmail(editedStudent.getEmail());
        studentRepository.save(student);
      } else {
        return false;
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return false;
    }
    return true;
  }
}
