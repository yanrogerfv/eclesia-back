package imdl.auth.controller;

import imdl.auth.dto.UserDTO;
import imdl.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/auth/user")
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
    public UserDTO create(@RequestBody UserDTO dto){
        return userService.create(dto);
    }

    @PutMapping
    @Operation(summary = "Update an user.")
    public UserDTO update(@RequestBody UserDTO dto){
        return userService.edit(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove an user.")
    public void delete(@RequestParam UUID id){
        userService.remove(id);
    }
}
