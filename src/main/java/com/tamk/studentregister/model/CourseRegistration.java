package com.tamk.studentregister.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(nullable = true)
  private Integer grade;

  public CourseRegistration(Course course, Student student) {
    this.course = course;
    this.student = student;
  }

  public CourseRegistration() {}
}
