package imdl.eclesia.auth.controller;

import imdl.eclesia.auth.configuration.AuthManager;
import imdl.eclesia.auth.configuration.JwtUtil;
import imdl.eclesia.auth.controller.input.LoginRequest;
import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.LoginOutput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.service.UserService;
import imdl.eclesia.domain.exception.RogueException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "v1/Login", description = "Login de usuário")
public class LoginController {

    private final AuthManager authenticationManager;
    private final UserService userService;

    public LoginController(AuthManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginOutput login(@RequestBody LoginRequest loginRequest){
        if(!userService.validateLogin(loginRequest))
            throw new RogueException("Login inválido");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        UserOutput user = userService.findByUsername(loginRequest.getUsername());

        return new LoginOutput(JwtUtil.generateToken(loginRequest.getUsername()), user);
    }


    @PutMapping("/update")
    @Operation(summary = "Update an user.")
    public UserOutput updateUser(@RequestBody UserInput input){
        return userService.updateUser(input);
    }

    @GetMapping("/validate-token")
    public boolean validateToken(){
        return true;
    }
}
