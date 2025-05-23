package imdl.eclesia.auth.service;

import imdl.eclesia.auth.controller.input.LoginRequest;
import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.dto.UserDTO;
import imdl.eclesia.auth.repository.UserRepository;
import imdl.eclesia.domain.LevitaResumed;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.service.LevitaService;
import imdl.eclesia.service.utils.MailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final LevitaService levitaService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);

    private final MailSender mailSender;

    public UserService(LevitaService levitaService, UserRepository userRepository, RoleService roleService, MailSender mailSender) {
        this.levitaService = levitaService;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailSender = mailSender;
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

    public void forgotPasswordStep1(String username){
        UserDTO dto = userRepository.findByUsername(username).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        mailSender.sendSimpleMessage("yanrogerfv@gmail.com", "Recuperação de senha", "Bip bop bip?");
//        dto.setPassword(crypt.encode("12345678"));
//        userRepository.save(UserDTO.toEntity(dto));
    }

    public void remove(UUID id){
        userRepository.delete(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found.")));
    }

    private UserOutput dtoToOutput(UserDTO dto){
        UserOutput output = new UserOutput();
        output.setId(dto.getId());
        output.setUsername(dto.getUsername());
        output.setRole(dto.getRole());
        if(dto.getLevitaId() != null)
            output.setLevita(levitaService.findById(dto.getLevitaId()));
        return output;
    }

    private UserDTO inputToDTO(UserInput input){
        UserDTO dto = new UserDTO();
        dto.setUsername(input.getUsername());
        if (input.getRole() == null)
            dto.setRole(roleService.list().stream().filter(r -> r.getRole().equals("Levita"))
                .findFirst().orElseThrow(() -> new RogueException("Cargo não encontrado.")));
        else dto.setRole(roleService.findById(input.getRole()));
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

    public boolean validateLogin(LoginRequest loginRequest) {
        UserDTO dto = userRepository.findByUsername(loginRequest.getUsername()).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return crypt.matches(loginRequest.getPassword(), dto.getPassword());
    }

    public UserOutput findByUsername(String username) {
        return dtoToOutput(UserDTO.toDTO(userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found."))));
    }

    public List<LevitaResumed> listLevitasWithoutLogin(){
        System.out.println("Listing levitas without login...");
        return levitaService.findAllResumed().stream().filter(levita -> !userRepository.existsByLevitaId(levita.getId())).toList();
    }

    public UserOutput activeUser() {
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
