package imdl.eclesia.auth.controller.output;

import imdl.eclesia.auth.dto.RoleDTO;
import imdl.eclesia.domain.Levita;
import lombok.Data;

import java.util.UUID;

@Data
public class UserOutput {
    private UUID id;
    private String username;
    private RoleDTO role;
    private Levita levita;
    private String accessCode;
}
