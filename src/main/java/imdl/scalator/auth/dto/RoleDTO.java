package imdl.scalator.auth.dto;

import imdl.scalator.auth.entity.RoleEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class RoleDTO {
    private UUID id;
    private String role;

    public static RoleEntity toEntity(RoleDTO dto){
        RoleEntity role = new RoleEntity();
        role.setId(dto.getId());
        role.setRole(dto.getRole());
        return role;
    }

    public static RoleDTO toDTO(RoleEntity role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        return dto;
    }
}
