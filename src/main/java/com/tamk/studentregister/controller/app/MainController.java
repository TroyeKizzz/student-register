package com.tamk.studentregister.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
  // GET localhost:8080/app
  @GetMapping("/app")
  public String getHomePage() {
    return "home.html";
  }

  @GetMapping("/")
  public String getRootPage() {
    return "redirect:/app";
  }
}
