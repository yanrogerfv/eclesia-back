package imdl.eclesia.auth.controller.output;

import imdl.eclesia.auth.dto.UserDTO;
import imdl.eclesia.domain.Levita;
import lombok.Data;

@Data
public class LoginOutput {
    private String token;
    private String username;
    private String role;
    private Levita levita;

    public LoginOutput(String token, UserOutput user) {
        this.token = token;
        this.username = user.getUsername();
        this.role = user.getRole().getRole();
        this.levita = user.getLevita();
    }
}
