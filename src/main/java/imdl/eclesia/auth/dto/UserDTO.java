package imdl.eclesia.auth.dto;

import imdl.eclesia.auth.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDTO implements UserDetails {
    private UUID id;
    private RoleDTO role;
    private String username;
    private String password;
    private UUID levitaId;
    private String accessCode;
    private boolean active;

    public UserDTO update(UserDTO userDTO) {
        this.role = userDTO.getRole();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        return this;
    }

    public static UserEntity toEntity(UserDTO dto){
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setRole(RoleDTO.toEntity(dto.getRole()));
        user.setUsername(dto.getUsername());
        user.setPasscode(dto.getPassword());
        user.setLevitaId(dto.getLevitaId());
        user.setAccessCode(dto.getAccessCode());
        user.setActive(dto.isActive());
        return user;
    }

    public static UserDTO toDTO(UserEntity user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setRole(RoleDTO.toDTO(user.getRole()));
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPasscode());
        dto.setLevitaId(user.getLevitaId());
        dto.setAccessCode(user.getAccessCode());
        dto.setActive(user.isActive());
        return dto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
