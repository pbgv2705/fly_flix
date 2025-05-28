package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo; // vídeo, texto, artigo, quiz

    private Integer ordem;

    private Integer duracaoEstimada; // em minutos

    private String linkConteudo; // URL, pode ser link para vídeo ou outro tipo de conteúdo

    @Lob
    @Column(name = "capa")
    private byte[] capa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;
}

