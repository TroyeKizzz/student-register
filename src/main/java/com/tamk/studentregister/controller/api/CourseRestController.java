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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tamk.studentregister.model.Course;
import com.tamk.studentregister.model.CourseRegistration;
import com.tamk.studentregister.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {
  @Autowired
  CourseService courseService;

  // GET localhost:8080/api/courses/1
  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable Long id ) {
    Optional<Course> course = courseService.findCourseById(id);
    
    if (course.isPresent()) {
      return new ResponseEntity<>(course.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // GET localhost:8080/api/courses
  @GetMapping
  public ResponseEntity<ArrayList<Course>> getAllCourses() {
    try {
      ArrayList<Course> courses = courseService.findAll();

      if (courses.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(courses, HttpStatus.OK);
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // POST localhost:8080/api/courses
  @PostMapping
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    try {
      Optional<Course> newCourse = courseService.createCourse(course);
      if (newCourse.isEmpty()) {
        // Already exists
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>(newCourse.get(), HttpStatus.CREATED);
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // POST localhost:8080/api/courses/1/enroll?studentId=1
  @PostMapping("/{courseId}/enroll")
  public ResponseEntity<CourseRegistration> enrollStudent(@PathVariable Long courseId, @RequestParam Long studentId) {
    try {
      Optional<CourseRegistration> courseRegistration = courseService.enroll(courseId, studentId);
      if (courseRegistration.isPresent()) {
        return new ResponseEntity<>(courseRegistration.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
