package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.config.TokenService;
import com.project.mestresala.mestresalabe.model.user.AuthenticationDTO;
import com.project.mestresala.mestresalabe.model.user.LoginResponseDTO;
import com.project.mestresala.mestresalabe.model.user.RegisterDTO;
import com.project.mestresala.mestresalabe.model.user.User;
import com.project.mestresala.mestresalabe.model.user.UserEmailDTO;
import com.project.mestresala.mestresalabe.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "User Auth")
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TokenService tokenService;

  @Operation(summary = "Login a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = LoginResponseDTO.class))}),
      @ApiResponse(responseCode = "403", description = "Incorrect email or password.",
          content = @Content)})
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @Operation(summary = "Register a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "400", description = "Field must not be null.",
          content = @Content)})
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody @Valid RegisterDTO data) {
    if(this.userRepository.findByEmail(data.email()) != null)
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "All fields must not be null."
      );

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.fullName(), data.email(), encryptedPassword, data.role());

    this.userRepository.save(newUser);
  }

  @Operation(summary = "Get a user by it's email.")
  @GetMapping("/user/{email}")
  public Long getUserIdByEmail(@PathVariable(value = "email") UserEmailDTO data) {
    System.out.println("User -> " + data.email());
    return ((User) userRepository.findByEmail(data.email())).getId();
  }

}
