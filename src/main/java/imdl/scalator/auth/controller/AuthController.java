package imdl.scalator.auth.controller;

import imdl.scalator.auth.entity.UserEntity;
import imdl.scalator.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @GetMapping("/test")
    public String test() {
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
        return "Test";
    }
}
