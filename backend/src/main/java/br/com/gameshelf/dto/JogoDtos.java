package br.com.gameshelf.dto;
import br.com.gameshelf.entity.StatusJogo;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public class JogoDtos {
 public record JogoRequest(@NotBlank @Size(max=140) String titulo,@Size(max=120) String desenvolvedora,@Size(max=80) String plataforma,@Size(max=80) String genero,@Min(1950) @Max(2100) Integer anoLancamento,@Size(max=1000) String capaUrl, @NotNull StatusJogo status,@DecimalMin("0.0") @DecimalMax("5.0") Double avaliacao,@Size(max=3000) String resenha){}
 public record JogoResponse(Long id,String titulo,String desenvolvedora,String plataforma,String genero,Integer anoLancamento,String capaUrl,StatusJogo status,Double avaliacao,String resenha,LocalDateTime criadoEm,LocalDateTime atualizadoEm){}
 public record DashboardResponse(long total,long concluidos,long jogando,long queroJogar,long abandonados,double mediaAvaliacao,java.util.List<JogoResponse> recentes){}
}
