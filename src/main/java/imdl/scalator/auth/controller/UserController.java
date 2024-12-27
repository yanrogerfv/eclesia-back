package imdl.scalator.auth.controller;

import imdl.scalator.auth.controller.input.UserInput;
import imdl.scalator.auth.dto.UserDTO;
import imdl.scalator.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/auth/user")
@Tag(name = "User", description = "Gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listing of all users.")
    public List<UserDTO> list(){
        return userService.list();
    }

    @PostMapping
    @Operation(summary = "Create a new user.")
    public UserDTO create(@RequestBody UserInput input){
        return userService.create(input);
    }

    @PutMapping
    @Operation(summary = "Update an user.")
    public UserDTO update(@RequestBody UserInput input){
        return userService.edit(input);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove an user.")
    public void delete(@RequestParam UUID id){
        userService.remove(id);
    }
}
