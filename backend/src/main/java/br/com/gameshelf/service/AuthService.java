package br.com.gameshelf.service;
import br.com.gameshelf.dto.AuthDtos.*;import br.com.gameshelf.entity.Usuario;import br.com.gameshelf.repository.UsuarioRepository;import br.com.gameshelf.security.JwtService;
import org.springframework.http.HttpStatus;import org.springframework.security.authentication.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;

@Service public class AuthService {
 private final UsuarioRepository repo;private final PasswordEncoder encoder;private final AuthenticationManager manager;private final JwtService jwt;
 public AuthService(UsuarioRepository r,PasswordEncoder e,AuthenticationManager m,JwtService j){repo=r;encoder=e;manager=m;jwt=j;}
 public AuthResponse registrar(RegistroRequest req){String email=req.email().trim().toLowerCase();if(repo.existsByEmail(email))throw new ResponseStatusException(HttpStatus.CONFLICT,"Este e-mail já está cadastrado.");Usuario u=repo.save(new Usuario(req.nome().trim(),email,encoder.encode(req.senha())));return response(u);}
 public AuthResponse login(LoginRequest req){try{manager.authenticate(new UsernamePasswordAuthenticationToken(req.email().trim().toLowerCase(),req.senha()));}catch(AuthenticationException e){throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"E-mail ou senha incorretos.");}Usuario u=repo.findByEmail(req.email().trim().toLowerCase()).orElseThrow();return response(u);}
 private AuthResponse response(Usuario u){return new AuthResponse(jwt.gerar(u.getEmail()),"Bearer",jwt.getExpiration(),new UsuarioResponse(u.getId(),u.getNome(),u.getEmail()));}
}
