package imdl.scalator.auth.service;

import imdl.scalator.auth.dto.UserDTO;
import imdl.scalator.auth.entity.UserEntity;
import imdl.scalator.auth.repository.RoleRepository;
import imdl.scalator.auth.repository.UserRepository;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.service.LevitaService;
import imdl.scalator.service.mapper.LevitaMapper;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final LevitaService levitaService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(LevitaService levitaService, UserRepository userRepository, RoleRepository roleRepository) {
        this.levitaService = levitaService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> list(){
        return userRepository.findAll().stream().map(UserDTO::toDTO).toList();
    }

    public UserDTO create(UserDTO dto){
        UserEntity entity = dto.toUser();
        entity.setLevitaId(levitaService.findById(dto.getLevitaId()).getId());
        return UserDTO.toDTO(userRepository.save(entity));
    }

    public UserDTO edit(UserDTO dto){
        UserEntity entity = userRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("User not found."));
        entity.setUsername(dto.getUsername());
        entity.setPasscode(dto.getPasscode());
        entity.setRoleId(roleRepository.findById(dto.getRole().getId()).orElseThrow(() -> new EntityNotFoundException("Role not found.")).getId());
        entity.setLevitaId(levitaService.findById(dto.getLevitaId()).getId());
        return UserDTO.toDTO(userRepository.save(dto.toUser()));
    }

    public void remove(UUID id){
        userRepository.delete(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found.")));
    }

}
