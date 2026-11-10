package br.com.gameshelf.controller;
import br.com.gameshelf.dto.JogoDtos.*;import br.com.gameshelf.repository.UsuarioRepository;import br.com.gameshelf.service.JogoService;import jakarta.validation.Valid;import org.springframework.http.HttpStatus;import org.springframework.security.core.Authentication;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/jogos")
public class JogoController {private final JogoService service;private final UsuarioRepository usuarios;public JogoController(JogoService s,UsuarioRepository u){service=s;usuarios=u;}private Long uid(Authentication a){return usuarios.findByEmail(((UserDetails)a.getPrincipal()).getUsername()).orElseThrow().getId();}
 @GetMapping public List<JogoResponse> listar(Authentication a){return service.listar(uid(a));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public JogoResponse criar(@Valid @RequestBody JogoRequest r,Authentication a){return service.criar(r,uid(a));}
 @GetMapping("/{id}") public JogoResponse obter(@PathVariable Long id,Authentication a){return service.obter(id,uid(a));}
 @PutMapping("/{id}") public JogoResponse atualizar(@PathVariable Long id,@Valid @RequestBody JogoRequest r,Authentication a){return service.atualizar(id,r,uid(a));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id,Authentication a){service.excluir(id,uid(a));}
}
