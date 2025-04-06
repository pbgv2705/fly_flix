package fly.be.flyflix.auth.service;

import fly.be.flyflix.auth.controller.dto.CadastroAlunoDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    public ResponseEntity<Map<String, Object>> cadastrarUsuario(CadastroAlunoDTO dto);
}
