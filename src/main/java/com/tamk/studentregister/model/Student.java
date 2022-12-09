package com.tamk.studentregister.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
  @Id
  private long id;

  private String firstName;
  private String lastName;
  private String email;

  public Student(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Student() {}
}
