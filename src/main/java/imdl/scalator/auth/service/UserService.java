package imdl.scalator.auth.service;

import imdl.scalator.auth.controller.input.UserInput;
import imdl.scalator.auth.controller.output.UserOutput;
import imdl.scalator.auth.dto.UserDTO;
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
    private final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);

    public UserService(LevitaService levitaService, UserRepository userRepository, RoleService roleService) {
        this.levitaService = levitaService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<UserOutput> list(){
        return userRepository.findAll().stream().map(UserDTO::toDTO).map(this::dtoToOutput).toList();
    }

    public UserOutput create(UserInput input){
        validate(input);
        UserDTO dto = inputToDTO(input);
        return dtoToOutput(UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto))));
    }

    public UserOutput edit(UserInput input){
        UserDTO dto = userRepository.findById(input.getId()).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        dto.setUsername(input.getUsername());
        dto.setRole(roleService.findById(input.getRole()));
        dto.setPassword(crypt.encode(input.getPasscode()));
        dto.setLevitaId(levitaService.findById(input.getLevitaId()).getId());
        return dtoToOutput(UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto))));
    }

    public void remove(UUID id){
        userRepository.delete(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found.")));
    }

    private UserOutput dtoToOutput(UserDTO dto){
        UserOutput output = new UserOutput();
        output.setId(dto.getId());
        output.setUsername(dto.getUsername());
        output.setRole(dto.getRole().getRole());
        if(dto.getLevitaId() != null)
            output.setLevita(levitaService.findById(dto.getLevitaId()));
        return output;
    }

    private UserDTO inputToDTO(UserInput input){
        UserDTO dto = new UserDTO();
        dto.setUsername(input.getUsername());
        dto.setRole(roleService.findById(input.getRole()));
        dto.setPassword(crypt.encode(input.getPasscode()));
        dto.setLevitaId(levitaService.findById(input.getLevitaId()).getId());
        return dto;
    }

    private void validate(UserInput input){
        if(input.getUsername() == null || input.getUsername().isBlank())
            throw new RogueException("Nome de usuário não deve estar vazio.");
        if(userRepository.existsByUsername(input.getUsername()))
            throw new RogueException("Já existe um cadastro com este nome de usuário.");
        if(input.getRole() == null)
            throw new RogueException("Cargo não selecionado.");
        if(input.getLevitaId() == null)
            throw new RogueException("Levita não selecionado.");
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

    public UserDTO findByUsername(String username) {
        return UserDTO.toDTO(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found.")));
    }
}
