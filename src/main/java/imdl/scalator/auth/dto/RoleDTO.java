package imdl.scalator.auth.dto;

import imdl.scalator.auth.entity.RoleEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDTO {
    private UUID id;
    private String role;

    public RoleEntity toRole(){
        RoleEntity role = new RoleEntity();
        role.setId(this.getId());
        role.setRole(this.getRole());
        return role;
    }

    public static RoleDTO toDTO(RoleEntity role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        return dto;
    }
}
