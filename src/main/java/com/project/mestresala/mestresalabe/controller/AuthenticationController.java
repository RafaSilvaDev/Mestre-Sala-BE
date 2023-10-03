package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.config.TokenService;
import com.project.mestresala.mestresalabe.model.user.AuthenticationDTO;
import com.project.mestresala.mestresalabe.model.user.LoginResponseDTO;
import com.project.mestresala.mestresalabe.model.user.RegisterDTO;
import com.project.mestresala.mestresalabe.model.user.User;
import com.project.mestresala.mestresalabe.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
    if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.fullName(), data.email(), encryptedPassword, data.role());

    this.userRepository.save(newUser);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return userRepository.findAll();
  }
}
