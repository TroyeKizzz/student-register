package com.tamk.studentregister.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
  private String title;
  private String description;

  @JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class, 
    property = "id")
  @OneToMany(mappedBy = "course")
  private Set<CourseRegistration> registrations = new HashSet<>();
  
  public Course(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public Course() {}
}
