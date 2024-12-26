package imdl.scalator.auth.service;

import imdl.scalator.auth.dto.RoleDTO;
import imdl.scalator.auth.entity.RoleEntity;
import imdl.scalator.auth.repository.RoleRepository;
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
        return RoleDTO.toDTO(roleRepository.save(RoleDTO.toEntity(dto)));
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
