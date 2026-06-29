package com.arashi.edu.arashynbe.features.ping;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminPingController {

  @GetMapping("/ping")
  public String ping() {
    return "Admin OK";
  }
}