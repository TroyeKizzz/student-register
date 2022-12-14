package com.tamk.studentregister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamk.studentregister.controller.app.StudentController;
import com.tamk.studentregister.model.Course;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests {

  @Autowired
  MockMvc mvc;

  @Autowired
  StudentController studentController;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void createGetAndDeleteCourse() throws Exception {
    // Create
    Course course = new Course("Cool course", "Cool course description");
    MvcResult result = mvc.perform(
      post("/api/courses")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(course))
    ).andExpect(status().isCreated())
    .andExpect(jsonPath("$.title").value("Cool course"))
    .andExpect(jsonPath("$.description").value("Cool course description"))
    .andReturn();
    course = objectMapper.readValue(result.getResponse().getContentAsString(), Course.class);

    // Get by id
    mvc.perform(
      get("/api/courses/{courseId}", course.getId())
    ).andExpect(status().isOk())
    .andExpect(jsonPath("$.title").value("Cool course"))
    .andExpect(jsonPath("$.description").value("Cool course description"));

    // Delete by id
    mvc.perform(
      post("/api/courses/{courseId}/delete", course.getId())
    ).andExpect(status().isOk())
    .andExpect(jsonPath("$").value(true));
  }

  @Test
  public void getAllCourses() throws Exception {
    mvc.perform(
      get("/api/courses")
    ).andExpect(status().isOk())
    .andExpect(jsonPath("$").isArray());
  }
}
