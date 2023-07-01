package fly.be.flyflix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
   @GetMapping
    public String inicio() {
        return "Bem-vindo ao Sistema FlyFlix! You totally   rock!!!";
    }
}
