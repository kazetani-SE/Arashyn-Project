package com.arashi.edu.arashynbe.features.ping;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {

  @GetMapping("")
  public String ping() {
    return "pong";
  }

  @GetMapping("/me")
  public String me() {
    return "Hello " + CurrentUser.getRole() + " " + CurrentUser.getId();
  }
}