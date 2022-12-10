package com.tamk.studentregister.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamk.studentregister.model.Course;
import com.tamk.studentregister.model.CourseRegistration;
import com.tamk.studentregister.model.Student;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
  Optional<CourseRegistration> findByStudentAndCourse(Student student, Course course);
}
