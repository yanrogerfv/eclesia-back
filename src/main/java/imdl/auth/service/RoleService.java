package imdl.auth.service;

import imdl.auth.dto.RoleDTO;
import imdl.auth.entity.RoleEntity;
import imdl.auth.repository.RoleRepository;
import imdl.scalator.domain.exception.EntityNotFoundException;

import java.util.List;

public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> list(){
        return roleRepository.findAll().stream().map(RoleDTO::toDTO).toList();
    }

    public RoleDTO create(RoleDTO dto){
        return RoleDTO.toDTO(roleRepository.save(dto.toRole()));
    }

    public RoleDTO edit(RoleDTO dto){
        RoleEntity entity = roleRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Role not found."));
        entity.setRole(dto.getRole());
        return RoleDTO.toDTO(roleRepository.save(entity));
    }

    public void remove(RoleDTO dto){
        roleRepository.delete(roleRepository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException("Role not found.")));
    }
}
