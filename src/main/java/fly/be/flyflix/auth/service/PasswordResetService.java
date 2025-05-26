package fly.be.flyflix.auth.service;
import fly.be.flyflix.auth.controller.dto.RedefinicaoSenhaDTO;
import fly.be.flyflix.auth.controller.dto.RequisicaoResetSenhaDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PasswordResetService {

    ResponseEntity<Map<String, Object>> solicitarResetSenha(RequisicaoResetSenhaDTO dto);

    ResponseEntity<Map<String, Object>> redefinirSenha(RedefinicaoSenhaDTO dto);


}


