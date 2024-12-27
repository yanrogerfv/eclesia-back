package imdl.scalator.auth.dto;

import imdl.scalator.auth.entity.UserEntity;
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
    private String passcode;
    private UUID levitaId;

    public static UserEntity toEntity(UserDTO dto){
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setRole(RoleDTO.toEntity(dto.getRole()));
        user.setUsername(dto.getUsername());
        user.setPasscode(dto.getPasscode());
        user.setLevitaId(dto.getLevitaId());
        return user;
    }

    public static UserDTO toDTO(UserEntity user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setRole(RoleDTO.toDTO(user.getRole()));
        dto.setUsername(user.getUsername());
        dto.setPasscode(user.getPasscode());
        dto.setLevitaId(user.getLevitaId());
        return dto;
    }

    public UserDTO(UUID id, RoleDTO role, String username, String passcode, UUID levitaId) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.passcode = passcode;
        this.levitaId = levitaId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    public String getPassword() {
        return this.passcode;
    }
}
