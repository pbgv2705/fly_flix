package fly.be.flyflix.auth.service;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
}
