package fly.be.flyflix.auth.controller;


import fly.be.flyflix.auth.controller.dto.ResetarSenha;
import fly.be.flyflix.auth.controller.dto.SolicitarResetSenha;
import fly.be.flyflix.auth.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenhaController {

    @Autowired
    private PasswordResetService passwordResetService;


    //Solicitar Resetar senha
    @PostMapping("/esqueci-senha")
    public ResponseEntity esqueciSenha(@RequestBody SolicitarResetSenha login) {
        return passwordResetService.solicitarResetSenha(login);
    }

    //Resetar senha
    @PostMapping("/resetar-senha")
    public ResponseEntity resetarSenha(@RequestBody ResetarSenha resetarSenha) {
        return passwordResetService.resetarSenha(resetarSenha);
    }
}
