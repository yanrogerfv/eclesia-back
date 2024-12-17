package imdl.scalator.auth.dto;

import imdl.scalator.auth.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private RoleDTO role;
    private String username;
    private String passcode;
    private UUID levitaId;

    public UserEntity toUser(){
        UserEntity user = new UserEntity();
        user.setId(this.getId());
        user.setRoleId(this.getRole().getId());
        user.setUsername(this.getUsername());
        user.setPasscode(this.getPasscode());
        user.setLevitaId(this.getLevitaId());
        return user;
    }
    public static UserDTO toDTO(UserEntity user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPasscode(user.getPasscode());
        dto.setLevitaId(user.getLevitaId());
        return dto;
    }
}
