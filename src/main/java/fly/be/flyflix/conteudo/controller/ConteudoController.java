package fly.be.flyflix.conteudo.controller;

import fly.be.flyflix.conteudo.entity.Conteudo;
import fly.be.flyflix.conteudo.repository.ConteudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conteudos")
public class ConteudoController {

    @Autowired
    private ConteudoRepository conteudoRepository;

    // GET paginado de conteúdos ativos
    @GetMapping
    public Page<Conteudo> listarConteudosAtivos(Pageable pageable) {
        return conteudoRepository.findAllByAtivoTrue(pageable);
    }

    // GET por ID
    @GetMapping("/{id}")
    public Conteudo buscarPorId(@PathVariable Long id) {
        return conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));
    }

    // POST para criar conteúdo
    @PostMapping
    public Conteudo criarConteudo(@RequestBody Conteudo conteudo) {
        return conteudoRepository.save(conteudo);
    }

    // PUT para atualizar conteúdo
    @PutMapping("/{id}")
    public Conteudo atualizarConteudo(@PathVariable Long id, @RequestBody Conteudo dadosAtualizados) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        conteudo.setTipo(dadosAtualizados.getTipo());
        conteudo.setUrl(dadosAtualizados.getUrl());
        conteudo.setTempoEstimadoLeitura(dadosAtualizados.getTempoEstimadoLeitura());
        conteudo.setTexto(dadosAtualizados.getTexto());
        conteudo.setAtivo(dadosAtualizados.isAtivo());

        return conteudoRepository.save(conteudo);
    }

    // DELETE lógico (desativa)
    @DeleteMapping("/{id}")
    public void desativarConteudo(@PathVariable Long id) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        conteudo.setAtivo(false);
        conteudoRepository.save(conteudo);
    }
}
