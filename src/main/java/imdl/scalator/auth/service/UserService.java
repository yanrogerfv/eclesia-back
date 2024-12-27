package imdl.scalator.auth.service;

import imdl.scalator.auth.controller.input.UserInput;
import imdl.scalator.auth.dto.UserDTO;
import imdl.scalator.auth.entity.UserEntity;
import imdl.scalator.auth.repository.UserRepository;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.service.LevitaService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final LevitaService levitaService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();

    public UserService(LevitaService levitaService, UserRepository userRepository, RoleService roleService) {
        this.levitaService = levitaService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<UserDTO> list(){
        return userRepository.findAll().stream().map(UserDTO::toDTO).toList();
    }

    public UserDTO create(UserInput input){
        validate(input);
        UserDTO dto = inputToDTO(input);
        return UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto)));
    }

    public UserDTO edit(UserInput input){
        UserDTO dto = userRepository.findById(input.getId()).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        dto.setUsername(input.getUsername());
        dto.setRole(roleService.findById(input.getRole()));
        dto.setPasscode(crypt.encode(input.getPasscode()));
        dto.setLevitaId(levitaService.findById(input.getLevitaId()).getId());
        return UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto)));
    }

    public void remove(UUID id){
        userRepository.delete(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found.")));
    }

    private UserDTO inputToDTO(UserInput input){
        UserDTO dto = new UserDTO();
        if(input.getId() != null)
            dto.setId(input.getId());
        dto.setUsername(input.getUsername());
        dto.setRole(roleService.findById(input.getRole()));
        dto.setPasscode(crypt.encode(input.getPasscode()));
//        dto.setLevitaId(levitaService.findById(input.getLevitaId()).getId());
        return dto;
    }

    private void validate(UserInput input){
        if(input.getUsername() == null || input.getUsername().isBlank())
            throw new RogueException("Nome de usuário não deve estar vazio.");
        if(userRepository.existsByUsername(input.getUsername()))
            throw new RogueException("Já existe um cadastro com este nome de usuário.");
        if(userRepository.existsByLevitaId(input.getLevitaId()))
            throw new RogueException("Já existe um cadastro para este Levita.");
        validatePassword(input.getPasscode());
    }

    private void validatePassword(String password){
        if(password == null || password.isBlank())
            throw new RogueException("Senha vazia!");
        if(password.length() < 8)
            throw new RogueException("A senha deve possuir 8 caracteres ou mais.");
    }

}
