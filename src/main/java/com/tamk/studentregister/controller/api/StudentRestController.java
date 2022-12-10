package com.tamk.studentregister.controller.api;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tamk.studentregister.model.Student;
import com.tamk.studentregister.service.StudentService;


@RestController
@RequestMapping("/api/students")
public class StudentRestController {
  @Autowired
  StudentService studentService;

  // GET localhost:8080/api/students/1
  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id ) {
    Optional<Student> student = studentService.findStudentById(id);
    
    if (student.isPresent()) {
      return new ResponseEntity<>(student.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // GET localhost:8080/api/students
  @GetMapping
  public ResponseEntity<ArrayList<Student>> getAllStudents() {
    try {
      ArrayList<Student> students = studentService.findAll();

      if (students.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(students, HttpStatus.OK);
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // POST localhost:8080/api/students
  @PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    try {
      Optional<Student> newStudent = studentService.createStudent(student);
      if (newStudent.isEmpty()) {
        // Already exists
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>(newStudent.get(), HttpStatus.CREATED);
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
}
