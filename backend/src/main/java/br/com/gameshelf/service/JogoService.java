
package br.com.gameshelf.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gameshelf.dto.JogoDtos.*;
import br.com.gameshelf.dto.JogoDtos.DashboardResponse;
import br.com.gameshelf.dto.JogoDtos.JogoRequest;
import br.com.gameshelf.dto.JogoDtos.JogoResponse;
import br.com.gameshelf.entity.Jogo;
import br.com.gameshelf.entity.StatusJogo;
import br.com.gameshelf.entity.Usuario;
import br.com.gameshelf.repository.JogoRepository;
import br.com.gameshelf.repository.UsuarioRepository;

@Service
public class JogoService {

    private final JogoRepository jogos;
    private final UsuarioRepository usuarios;

    public JogoService(JogoRepository j, UsuarioRepository u) {
        jogos = j;
        usuarios = u;
    }

    private Usuario usuario(Long id) {
        return usuarios.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }

    private Jogo buscar(Long id, Long uid) {
        return jogos.findByIdAndUsuario_Id(id, uid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Jogo não encontrado na sua biblioteca."));
    }

    private JogoResponse dto(Jogo j) {
        return new JogoResponse(
                j.getId(),
                j.getTitulo(),
                j.getDesenvolvedora(),
                j.getPlataforma(),
                j.getGenero(),
                j.getAnoLancamento(),
                j.getCapaUrl(),
                j.getStatus(),
                j.getAvaliacao(),
                j.getResenha(),
                j.getCriadoEm(),
                j.getAtualizadoEm()
        );
    }

    public List<JogoResponse> listar(Long uid) {
        return jogos.findAllByUsuario_IdOrderByAtualizadoEmDesc(uid)
                .stream()
                .map(this::dto)
                .toList();
    }

    public JogoResponse obter(Long id, Long uid) {
        return dto(buscar(id, uid));
    }

    public JogoResponse criar(JogoRequest r, Long uid) {
        Jogo jogo = new Jogo(
                r.titulo().trim(),
                r.desenvolvedora(),
                r.plataforma(),
                r.genero(),
                r.anoLancamento(),
                r.capaUrl(),
                r.status(),
                r.avaliacao(),
                r.resenha(),
                usuario(uid)
        );

        return dto(jogos.save(jogo));
    }

    public JogoResponse atualizar(Long id, JogoRequest r, Long uid) {
        Jogo j = buscar(id, uid);

        j.atualizar(
                r.titulo().trim(),
                r.desenvolvedora(),
                r.plataforma(),
                r.genero(),
                r.anoLancamento(),
                r.capaUrl(),
                r.status(),
                r.avaliacao(),
                r.resenha()
        );

        return dto(jogos.save(j));
    }

    public void excluir(Long id, Long uid) {
        jogos.delete(buscar(id, uid));
    }

    public DashboardResponse dashboard(Long uid) {
        List<Jogo> todos =
                jogos.findAllByUsuario_IdOrderByAtualizadoEmDesc(uid);

        double media = todos.stream()
                .filter(j -> j.getAvaliacao() != null)
                .mapToDouble(Jogo::getAvaliacao)
                .average()
                .orElse(0);

        return new DashboardResponse(
                todos.size(),
                jogos.countByUsuario_IdAndStatus(uid, StatusJogo.CONCLUIDO),
                jogos.countByUsuario_IdAndStatus(uid, StatusJogo.JOGANDO),
                jogos.countByUsuario_IdAndStatus(uid, StatusJogo.QUERO_JOGAR),
                jogos.countByUsuario_IdAndStatus(uid, StatusJogo.ABANDONADO),
                Math.round(media * 10.0) / 10.0,
                todos.stream().limit(5).map(this::dto).toList()
        );
    }
}