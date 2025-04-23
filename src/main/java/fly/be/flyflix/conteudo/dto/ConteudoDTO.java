package fly.be.flyflix.conteudo.dto;

import fly.be.flyflix.conteudo.entity.Conteudo;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ConteudoDTO {

    private Long id;
    private Conteudo.TipoConteudo tipo; // Corrigido para o enum diretamente
    private String url; // para vídeos, PDFs ou links externos
    private String texto; // usado para conteúdo textual ou HTML/Markdown
    private Integer tempoEstimadoLeitura; // usado para textos e PDFs
    private boolean ativo;

}


