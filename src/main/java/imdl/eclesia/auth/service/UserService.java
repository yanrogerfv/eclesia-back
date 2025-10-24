package imdl.eclesia.auth.service;

import imdl.eclesia.auth.controller.input.LoginRequest;
import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.dto.CreateUserOutput;
import imdl.eclesia.auth.dto.UserDTO;
import imdl.eclesia.auth.repository.UserRepository;
import imdl.eclesia.domain.Levita;
import imdl.eclesia.domain.LevitaResumed;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.service.LevitaService;
import imdl.eclesia.service.utils.mail.AppMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

@Slf4j
public class UserService {

    private final LevitaService levitaService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);

    private final AppMailSender appMailSender;

    public UserService(LevitaService levitaService, UserRepository userRepository, RoleService roleService, AppMailSender appMailSender) {
        this.levitaService = levitaService;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.appMailSender = appMailSender;
    }

    public List<UserOutput> list(){
        return userRepository.findAll().stream().map(UserDTO::toDTO).map(this::dtoToOutput).toList();
    }

    public CreateUserOutput createUserNotActive(UUID levitaId){
        UserDTO dto = new UserDTO();

        if (userRepository.existsByLevitaId(levitaId))
            throw new RogueException("Já existe um cadastro para este Levita.");
        Levita levita = levitaService.findById(levitaId);

        dto.setUsername(levita.getNome().trim().toLowerCase().replace(" ", "."));
        dto.setLevitaId(levitaId);

        dto.setAccessCode(generateAccessCode());

        userRepository.save(UserDTO.toEntity(dto));

        return new CreateUserOutput(levita.getNome(), dto.getAccessCode());
    }

    public UserOutput register(UserInput input){
        validate(input);
        UserDTO dto = inputToDTO(input);
        return dtoToOutput(UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto))));
    }

    public UserOutput edit(UserInput input){
        validate(input);
        UserDTO dto = userRepository.findById(input.getId()).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        dto.setUsername(input.getUsername());
        dto.setRole(roleService.findById(input.getRole()));
        dto.setPassword(crypt.encode(input.getPasscode()));
        return dtoToOutput(UserDTO.toDTO(userRepository.save(UserDTO.toEntity(dto))));
    }

    public void forgotPasswordStep1(String username){
        UserDTO dto = userRepository.findByUsername(username).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        appMailSender.passwordResetEmail(levitaService.findById(dto.getLevitaId()).getEmail(), "youtube.com");
        log.info("Password recovery email sent to user: {}", dto.getUsername());
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

        if(!userRepository.existsByAccessCode(input.getAccessCode()))
            throw new RogueException("Código de acesso inválido.");

        if(input.getUsername() == null || input.getUsername().isBlank())
            throw new RogueException("Nome de usuário não deve estar vazio.");
        if(userRepository.existsByUsername(input.getUsername()))
            throw new RogueException("Já existe um cadastro com este nome de usuário.");
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
        log.info("Listing levitas without login...");
        return levitaService.findAllResumed().stream().filter(levita -> !userRepository.existsByLevitaId(levita.getId())).toList();
    }

    public UserOutput activeUser() {
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void restore(UUID id) {
        UserDTO dto = userRepository.findById(id).map(UserDTO::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        log.info("Restoring password for user: {}", dto.getUsername());
        Levita levita = levitaService.findById(dto.getLevitaId());

        dto.setPassword(crypt.encode("12345678"));
        userRepository.save(UserDTO.toEntity(dto));
//        if(levita.getEmail() == null || levita.getEmail().isBlank()) {
//            log.warn("Levita with ID {} has no email set, cannot send password restoration email.", dto.getLevitaId());
//            throw new RogueException("Levita has no email set.");
//        }
        if (levita.getEmail() != null)
            appMailSender.sendSimpleMessage(levita.getEmail(), "Recuperação de senha - Eclesia Software", "Sua senha foi restaurada. Por favor, altere-a assim que possível.");
        log.info("Password restored for user: {}", dto.getUsername());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(dto.getUsername())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        log.info("User logged out after password restoration.");
        // Optionally, you can log out the user or invalidate the session here

    }

    private String generateAccessCode(){
        String accessCode = null;
        while (userRepository.existsByAccessCode(accessCode) || accessCode == null)
            accessCode = AccessCodeGenerator.generateAccessCode();
        return accessCode;
    }

    public String generateNewAccessCode(UUID userId) {
        UserDTO dto = userRepository.findById(userId).map(UserDTO::toDTO)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String newAccessCode = generateAccessCode();
        dto.setAccessCode(newAccessCode);
        userRepository.save(UserDTO.toEntity(dto));
        log.info("New access code generated for user: {}", dto.getUsername());
        return newAccessCode;
    }
}
