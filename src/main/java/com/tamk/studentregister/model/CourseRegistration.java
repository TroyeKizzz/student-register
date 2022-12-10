package com.tamk.studentregister.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "course_registrations")
public class CourseRegistration extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  private int grade;

  public CourseRegistration(Course course, Student student) {
    this.course = course;
    this.student = student;
  }

  public CourseRegistration() {}
}
