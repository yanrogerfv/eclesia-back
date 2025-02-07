package imdl.eclesia.auth.controller.output;

import imdl.eclesia.domain.Levita;
import lombok.Data;

import java.util.UUID;

@Data
public class UserOutput {
    private UUID id;
    private String username;
    private String role;
    private Levita levita;
}
