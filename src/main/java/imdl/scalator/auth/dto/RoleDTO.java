package imdl.scalator.auth.dto;

import imdl.scalator.auth.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
