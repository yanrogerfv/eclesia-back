package imdl.eclesia.auth.controller;

import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.dto.CreateUserOutput;
import imdl.eclesia.auth.dto.RoleDTO;
import imdl.eclesia.auth.service.RoleService;
import imdl.eclesia.auth.service.UserService;
import imdl.eclesia.domain.LevitaResumed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication and Authorization - Management of users and roles.")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;

    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    @Operation(summary = "List of all users.")
    public List<UserOutput> listUsers(){
        return userService.list();
    }

    @GetMapping("/user/active")
    @Operation(summary = "Return the logged user.")
    public UserOutput active(){
        return userService.activeUser();
    }

    @PostMapping("/user")
    @Operation(summary = "Create a new user.")
    public CreateUserOutput createUser(@RequestBody UUID levitaId){
        return userService.createUserNotActive(levitaId);
    }

    @PatchMapping("/user/restore/{id}")
    @Operation(summary = "Restore a user's password.")
    public void restoreUser(@PathVariable UUID id){
        userService.restore(id);
    }

    @PutMapping("/user")
    @Operation(summary = "Update an user.")
    public UserOutput updateUser(@RequestBody UserInput input){
        return userService.updateUser(input);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Remove an user.")
    public void deleteUser(@PathVariable UUID id){
        userService.remove(id);
    }

    @GetMapping("/user/levita-x")
    @Operation(summary = "List of all levitas without login.")
    public List<LevitaResumed> listLevitasWithoutLogin(){
        return userService.listLevitasWithoutLogin();
    }

    @GetMapping("/role")
    @Operation(summary = "List of all roles.")
    public List<RoleDTO> listRoles(){
        return roleService.list();
    }

    @GetMapping("/role/{username}")
    @Operation(summary = "List of all roles of a user.")
    public RoleDTO listRolesOfUser(@PathVariable String username){
        return userService.findByUsername(username).getRole();
    }

    @PostMapping("/role")
    @Operation(summary = "Create a new role.")
    public RoleDTO createRole(@RequestBody RoleDTO dto){
        return roleService.create(dto);
    }

    @PutMapping("/role")
    @Operation(summary = "Update a role.")
    public RoleDTO updateRole(@RequestBody RoleDTO dto){
        return roleService.edit(dto);
    }

    @DeleteMapping("/role/{id}")
    @Operation(summary = "Remove a role.")
    public void deleteRole(@RequestParam UUID id){
        roleService.remove(id);
    }

    @GetMapping("/test")
    public String test() {
        return SecurityContextHolder.getContext().getAuthentication().toString();
    }

    @GetMapping("/recover")
    public void recover(String username) {
        userService.forgotPasswordStep1(username);
    }
}
