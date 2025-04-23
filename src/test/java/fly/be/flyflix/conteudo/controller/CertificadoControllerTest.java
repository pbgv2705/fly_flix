package fly.be.flyflix.conteudo.controller;
import fly.be.flyflix.FlyflixApplication;
import fly.be.flyflix.conteudo.service.CertificadoService;
import fly.be.flyflix.testUtils.JwtTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {FlyflixApplication.class, CertificadoController.class})
@AutoConfigureMockMvc
@Import(JwtTestUtils.class)
@ActiveProfiles("test") // Ativa o perfil de teste
public class CertificadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTestUtils jwtTestUtils;

    @MockBean
    private CertificadoService certificadoService;

    @Test
    public void deveRetornar200QuandoAdmin() throws Exception {
        // Arrange
        Long alunoId = 1L;
        Long cursoId = 1L;

        when(certificadoService.podeEmitirCertificado(alunoId,cursoId)).thenReturn(true); // ou o objeto que seu controller espera

        // Act & Assert
        mockMvc.perform(get("/api/certificados/eligibilidade")
                        .param("alunoId", alunoId.toString())
                        .param("cursoId", cursoId.toString())
                        .with(jwtTestUtils.adminUser()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Elegível para certificado"));
    }
    @Test
    public void deveRetornar200QuandoAdminNaVerificacaoDeElegibilidade() throws Exception {
        Long alunoId = 1L;
        Long cursoId = 1L;

        when(certificadoService.podeEmitirCertificado(alunoId, cursoId)).thenReturn(true);

        mockMvc.perform(get("/api/certificados/eligibilidade")
                        .param("alunoId", alunoId.toString())
                        .param("cursoId", cursoId.toString())
                        .with(jwtTestUtils.adminUser()))
                .andExpect(status().isOk())
                .andExpect(content().string("Elegível para certificado"));
    }

    @Test
    public void deveRetornar403SeNaoElegivel() throws Exception {
        Long alunoId = 1L;
        Long cursoId = 1L;

        when(certificadoService.podeEmitirCertificado(alunoId, cursoId)).thenReturn(false);

        mockMvc.perform(get("/api/certificados/eligibilidade")
                        .param("alunoId", alunoId.toString())
                        .param("cursoId", cursoId.toString())
                        .with(jwtTestUtils.adminUser()))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Requisitos não cumpridos"));
    }

    @Test
    public void deveBaixarCertificadoComSucesso() throws Exception {
        Long alunoId = 1L;
        Long cursoId = 1L;

        byte[] fakePdf = "PDF_SIMULADO".getBytes();

        when(certificadoService.gerarCertificado(alunoId, cursoId)).thenReturn(fakePdf);

        mockMvc.perform(get("/api/certificados/download")
                        .param("alunoId", alunoId.toString())
                        .param("cursoId", cursoId.toString())
                        .with(jwtTestUtils.adminUser()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado.pdf"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes(fakePdf));
    }


}
