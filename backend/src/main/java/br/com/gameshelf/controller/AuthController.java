package br.com.gameshelf.controller;
import br.com.gameshelf.dto.AuthDtos.*;import br.com.gameshelf.service.AuthService;import jakarta.validation.Valid;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {private final AuthService service;public AuthController(AuthService s){service=s;}@PostMapping("/registro") @ResponseStatus(HttpStatus.CREATED) public AuthResponse registrar(@Valid @RequestBody RegistroRequest r){return service.registrar(r);}@PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return service.login(r);}}
