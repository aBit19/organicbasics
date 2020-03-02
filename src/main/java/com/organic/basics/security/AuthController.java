package com.organic.basics.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AccessControlService accessControlService;

  @PostMapping("/singin")
  public ResponseEntity<SignInResponse> singin(@RequestBody SignDTO signDTO) {
    return ResponseEntity.ok(accessControlService.authenticate(signDTO));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignDTO signUpDTO) {
    if (accessControlService.registerUser(signUpDTO)) {
      return ResponseEntity.ok("You have been registered successfully");
    } else {
      return ResponseEntity.badRequest().body("Username already in use.");
    }
  }

}
