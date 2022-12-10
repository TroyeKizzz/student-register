package com.tamk.studentregister.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamk.studentregister.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
  @Override
  public ArrayList<Course> findAll();
  public Optional<Course> findByTitle(String title);
}
