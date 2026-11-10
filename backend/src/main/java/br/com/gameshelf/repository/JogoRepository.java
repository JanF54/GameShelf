
package br.com.gameshelf.repository;

import br.com.gameshelf.entity.Jogo;
import br.com.gameshelf.entity.StatusJogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    List<Jogo> findAllByUsuario_IdOrderByAtualizadoEmDesc(Long usuarioId);

    Optional<Jogo> findByIdAndUsuario_Id(Long id, Long usuarioId);

    long countByUsuario_Id(Long usuarioId);

    long countByUsuario_IdAndStatus(Long usuarioId, StatusJogo status);
}
