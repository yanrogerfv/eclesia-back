package imdl.scalator.auth.controller;

import imdl.scalator.auth.controller.input.LoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "v1/Login", description = "Login de usuário")
public class LoginController {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
