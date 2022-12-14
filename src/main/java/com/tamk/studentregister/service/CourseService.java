package com.tamk.studentregister.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamk.studentregister.model.Course;
import com.tamk.studentregister.model.CourseRegistration;
import com.tamk.studentregister.model.Student;
import com.tamk.studentregister.repository.CourseRegistrationRepository;
import com.tamk.studentregister.repository.CourseRepository;

@Service
public class CourseService {
  @Autowired
  private CourseRepository courseRepository;

  @Autowired 
  private CourseRegistrationRepository courseRegistrationRepository;

  @Autowired
  private StudentService studentService;

  public Optional<Course> findCourseById(Long id) {
    return courseRepository.findById(id);
  }

  public ArrayList<Course> findAll() {
    return courseRepository.findAll();
  }

  public Optional<Course> createCourse(Course course) {
    Optional<Course> existing = courseRepository.findByTitle(course.getTitle());
    Optional<Course> newCourse = Optional.empty();
    if (existing.isEmpty()) {
      newCourse = Optional.of(courseRepository.save(new Course(
        course.getTitle(),
        course.getDescription()
      )));
    }
    return newCourse;
  }

  public Optional<CourseRegistration> enroll(Long courseId, Long studentId) throws Exception {
    Optional<Course> courseResult = this.findCourseById(courseId);
    Optional<Student> studentResult = studentService.findStudentById(studentId);

    if (courseResult.isPresent() && studentResult.isPresent()) {
      Student student = studentResult.get();
      Course course = courseResult.get();
      Optional<CourseRegistration> courseRegistration = courseRegistrationRepository.findByStudentAndCourse(student, course);
      if (courseRegistration.isPresent()) {
        return Optional.empty();
      }
      return Optional.of(courseRegistrationRepository.save(new CourseRegistration(course, student)));
    }
    throw new Exception("Course or Student not found");
  }

  public Optional<CourseRegistration> enroll(Long courseId, String studentEmail) throws Exception { 
    Optional<Course> courseResult = this.findCourseById(courseId);
    Optional<Student> studentResult = studentService.findStudentByEmail(studentEmail);

    if (courseResult.isPresent() && studentResult.isPresent()) {
      Student student = studentResult.get();
      Course course = courseResult.get();
      Optional<CourseRegistration> courseRegistration = courseRegistrationRepository.findByStudentAndCourse(student, course);
      if (courseRegistration.isPresent()) {
        return Optional.empty();
      }
      return Optional.of(courseRegistrationRepository.save(new CourseRegistration(course, student)));
    }
    throw new Exception("Course or Student not found");
  }

  public boolean edit(Long courseId, Course editedCourse) {
    try {
      Optional<Course> searchResult = courseRepository.findById(courseId);
      if (searchResult.isPresent()) {
        Course course = searchResult.get();
        course.setDescription(editedCourse.getDescription());
        course.setTitle(editedCourse.getTitle());
        courseRepository.save(course);
      } else {
        return false;
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return false;
    }
    return true;
  }

  public Optional<Course> delete(Long courseId) {
    try {
      Optional<Course> searchResult = courseRepository.findById(courseId);
      if (searchResult.isPresent()) {
        courseRepository.delete(searchResult.get());
        return searchResult;
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return Optional.empty();
    }
    return Optional.empty();
  }
}
