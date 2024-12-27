package imdl.scalator.auth.controller.output;

import imdl.scalator.domain.Levita;
import lombok.Data;

import java.util.UUID;

@Data
public class UserOutput {
    private UUID id;
    private String username;
    private String role;
    private Levita levita;
}
