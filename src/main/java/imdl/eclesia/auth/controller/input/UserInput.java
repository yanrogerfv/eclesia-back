package imdl.eclesia.auth.controller.input;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInput {
    private UUID role;
    private String username;
    private String passcode;
    private String accessCode;
    private UUID levitaId;
}
