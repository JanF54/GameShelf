package br.com.gameshelf.dto;
import jakarta.validation.constraints.*;
public class AuthDtos {
 public record RegistroRequest(@NotBlank @Size(max=100) String nome,@NotBlank @Email @Size(max=160) String email,@NotBlank @Size(min=8,max=72) String senha){}
 public record LoginRequest(@NotBlank @Email String email,@NotBlank String senha){}
 public record AuthResponse(String token,String tipo,long expiracaoMs,UsuarioResponse usuario){}
 public record UsuarioResponse(Long id,String nome,String email){}
}
