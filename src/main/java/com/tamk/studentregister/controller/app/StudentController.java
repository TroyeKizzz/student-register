package com.tamk.studentregister.controller.app;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tamk.studentregister.model.Student;
import com.tamk.studentregister.service.StudentService;

@RequestMapping("/app/students")
@Controller
public class StudentController {
  @Autowired
  StudentService studentService;

  // GET localhost:8080/app/students/1
  @GetMapping("/{id}")
  public String getStudentById(@PathVariable Long id, Model model) {
    Optional<Student> student = studentService.findStudentById(id);
    
    if (student.isPresent()) {
      model.addAttribute("student", student.get());
      return "student.html";
    } else {
      return "not-found.html";
    }
  }

  // GET localhost:8080/app/students
  @GetMapping
  public String getAllStudents(Model model) {
    try {
      ArrayList<Student> students = studentService.findAll();

      if (students.isEmpty()) {
        return "not-found.html";
      } else {
        model.addAttribute("students", students);
        return "students.html";
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return "not-found.html";
    }
  }

  // POST localhost:8080/app/courses
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String createStudent(Student student, Model model) {
    try {
      Optional<Student> newStudent = studentService.createStudent(student);
      ArrayList<Student> students = studentService.findAll();
      model.addAttribute("students", students);
      if (newStudent.isEmpty()) {
        // Already exists
        model.addAttribute("error", "Email already registered");
        return "students.html";
      } else {
        return "redirect:/app/students";
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return "not-found";
    }
  }

  // POST localhost:8080/app/students/1/edit
  @PostMapping(path = "/{studentId}/edit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String save(@PathVariable Long studentId, Student course) {
    boolean res = studentService.edit(studentId, course);
    if (res) {
      return "redirect:/app/students/" + studentId;
    } else {
      return "not-found.html";
    }
  }

  // GET localhost:8080/app/students/1/edit
  @GetMapping("/{studentId}/edit")
  public String edit(@PathVariable Long studentId, Model model) {
    Optional<Student> student = studentService.findStudentById(studentId);
    if (student.isPresent()) {
      model.addAttribute("student", student.get());
      return "edit-student.html";
    } else {
      return "not-found.html";
    }
  }
}
