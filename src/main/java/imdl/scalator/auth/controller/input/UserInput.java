package imdl.scalator.auth.controller.input;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInput {
    private UUID id;
    private UUID role;
    private String username;
    private String passcode;
    private UUID levitaId;
}
