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
import org.springframework.web.bind.annotation.RequestParam;

import com.tamk.studentregister.model.Course;
import com.tamk.studentregister.model.CourseRegistration;
import com.tamk.studentregister.service.CourseService;

@RequestMapping("/app/courses")
@Controller
public class CourseController {
  @Autowired
  CourseService courseService;

  // GET localhost:8080/app/courses/1
  @GetMapping("/{id}")
  public String getCourseById(@PathVariable Long id, Model model) {
    Optional<Course> course = courseService.findCourseById(id);
    
    if (course.isPresent()) {
      model.addAttribute("course", course.get());
      return "course.html";
    } else {
      return "not-found.html";
    }
  }

  // GET localhost:8080/app/courses
  @GetMapping
  public String getAllCourses(Model model) {
    try {
      ArrayList<Course> courses = courseService.findAll();

      if (courses.isEmpty()) {
        return "not-found.html";
      } else {
        model.addAttribute("courses", courses);
        return "courses.html";
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return "not-found.html";
    }
  }

  // POST localhost:8080/app/courses
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String createCourse(Course course, Model model) {
    try {
      Optional<Course> newCourse = courseService.createCourse(course);
      ArrayList<Course> courses = courseService.findAll();
      model.addAttribute("courses", courses);
      if (newCourse.isEmpty()) {
        // Already exists
        model.addAttribute("error", "Course already exists");
        return "courses.html";
      } else {
        return "redirect:/app/courses";
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return "not-found";
    }
  }

  // POST localhost:8080/api/courses/1/enroll
  @PostMapping("/{courseId}/enroll")
  public String enrollStudent(@PathVariable Long courseId, @RequestParam(required = false) Long studentId, @RequestParam(required = false) String studentEmail, Model model) {
    try {
      Optional<CourseRegistration> courseRegistration = Optional.empty();
      if (studentEmail != null) {
        courseRegistration = courseService.enroll(courseId, studentEmail);
      } else if (studentId != null) {
        courseRegistration = courseService.enroll(courseId, studentId);
      } else {
        throw new Exception("Neither studentId or studentEmail are provided");
      }
      if (courseRegistration.isPresent()) {
        return "redirect:/app/courses";
      } else {
        model.addAttribute("error", "You are already enrolled");
        model.addAttribute("courses", courseService.findAll());
        return "courses.html";
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return "not-found.html";
    }
  }
}
