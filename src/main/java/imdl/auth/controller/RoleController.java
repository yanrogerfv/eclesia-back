package imdl.auth.controller;

import imdl.auth.dto.RoleDTO;
import imdl.auth.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDTO> list(){
        return roleService.list();
    }

    @PostMapping
    public RoleDTO create(@RequestBody RoleDTO dto){
        return roleService.create(dto);
    }

    @PutMapping
    public RoleDTO update(@RequestBody RoleDTO dto){
        return roleService.edit(dto);
    }

    @DeleteMapping
    public void delete(@RequestBody RoleDTO dto){
        roleService.remove(dto);
    }
}
