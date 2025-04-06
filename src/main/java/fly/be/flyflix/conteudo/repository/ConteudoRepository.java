package fly.be.flyflix.conteudo.repository;

import fly.be.flyflix.conteudo.entity.Conteudo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {
    Page<Conteudo> findAllByAtivoTrue(Pageable paginacao);
}
