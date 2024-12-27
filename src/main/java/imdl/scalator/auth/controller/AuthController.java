package imdl.scalator.auth.controller;

import imdl.scalator.auth.dto.RoleDTO;
import imdl.scalator.auth.dto.UserDTO;
import imdl.scalator.auth.entity.UserEntity;
import imdl.scalator.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @GetMapping("/test")
    public List<String> test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserDTO user = new UserDTO(UUID.randomUUID(), new RoleDTO(UUID.randomUUID(), "ADMIN"), "Nome do Usuário", "Passcode", null);
        UserDTO user2 = new UserDTO(UUID.randomUUID(), new RoleDTO(UUID.randomUUID(), "ADMIN"), "USUÁRIO 2", bCryptPasswordEncoder.encode("Passcode"), null);
        return List.of(user.toString(), user2.toString(), SecurityContextHolder.getContext().getAuthentication().toString());
    }
}
