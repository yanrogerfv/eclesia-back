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
import java.util.Map;
import java.util.UUID;

import imdl.eclesia.domain.exception.UnauthorizedException;
import imdl.eclesia.service.AppService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication and Authorization - Management of users and roles.")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final AppService appService;

    public AuthController(UserService userService, RoleService roleService, AppService appService) {
        this.userService = userService;
        this.roleService = roleService;
        this.appService = appService;
    }

    @GetMapping("/user")
    @Operation(summary = "List of all users.")
    public List<UserOutput> listUsers(){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem listar usuários.");
        return userService.list();
    }

    @GetMapping("/user/active")
    @Operation(summary = "Return the logged user.")
    public UserOutput active(){
        return userService.activeUser();
    }

    @GetMapping("/user/inactive")
    @Operation(summary = "List of all inactive users.")
    public List<UserOutput> listInactiveUsers(){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem listar usuários inativos.");
        return userService.listAllNotActive();
    }

    @PostMapping("/user")
    @Operation(summary = "Create a new user.")
    public CreateUserOutput createUser(@RequestBody UserInput userInput){
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem criar usuários.");
        if (!appService.isAdmin()) userInput.setRole(null);
        return userService.createUserNotActive(userInput);
    }

    @PatchMapping("/user/restore/{id}")
    @Operation(summary = "Restore a user's password.")
    public void restoreUser(@PathVariable UUID id){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem restaurar a senha de um usuário.");
        userService.restore(id);
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Remove an user.")
    public void deleteUser(@PathVariable UUID id){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem remover usuários.");
        userService.remove(id);
    }

    @PatchMapping("/user/deactivate/{id}")
    @Operation(summary = "Deactivate a user without deleting.")
    public void deactivateUser(@PathVariable UUID id){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem desativar usuários.");
        userService.deactivate(id);
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "Update a user's profile (username, password, role).")
    public UserOutput updateProfile(@PathVariable UUID id, @RequestBody UserInput input){
        if (!appService.isAdmin() && !userService.activeUser().getId().equals(id)) {
            throw new UnauthorizedException("Apenas o próprio usuário ou administradores podem editar o perfil.");
        }
        if (!appService.isAdmin()) {
            input.setRole(null); // Apenas ADMIN pode alterar cargo
        }
        return userService.updateProfile(id, input);
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
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem criar cargos.");
        return roleService.create(dto);
    }

    @PutMapping("/role")
    @Operation(summary = "Update a role.")
    public RoleDTO updateRole(@RequestBody RoleDTO dto){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem editar cargos.");
        return roleService.edit(dto);
    }

    @DeleteMapping("/role/{id}")
    @Operation(summary = "Remove a role.")
    public void deleteRole(@RequestParam UUID id){
        if (!appService.isAdmin()) throw new UnauthorizedException("Apenas administradores podem remover cargos.");
        roleService.remove(id);
    }

    @GetMapping("/recover")
    public String getNewCodeForUser(UUID userId) {
        return userService.generateNewAccessCode(userId);
    }
}
