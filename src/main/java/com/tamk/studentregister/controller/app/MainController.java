package com.tamk.studentregister.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/app")
@Controller
public class MainController {
  // GET localhost:8080/app
  @GetMapping
  public String getHomePage() {
    return "home.html";
  }
}
