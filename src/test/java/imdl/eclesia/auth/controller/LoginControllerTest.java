package imdl.eclesia.auth.controller;

import imdl.eclesia.auth.configuration.AuthManager;
import imdl.eclesia.auth.controller.input.LoginRequest;
import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.service.UserService;
import imdl.eclesia.domain.exception.RogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private AuthManager authManager;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginSuccessTest() throws Exception {
        when(userService.validateLogin(any(LoginRequest.class))).thenReturn(true);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userService.findByUsername(anyString())).thenReturn(mock(UserOutput.class));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"pass123\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).validateLogin(any());
        verify(authManager, times(1)).authenticate(any());
        verify(userService, times(1)).findByUsername(any());
    }

    // Since mockMvc wrapper handles exceptions via GlobalExceptionHandler we can just test if the controller method throws it.
    // However, standaloneSetup without ControllerAdvice will cause nested ServletException. That is expected.
    @Test
    public void loginInvalidThrowsExceptionTest() throws Exception {
        when(userService.validateLogin(any(LoginRequest.class))).thenReturn(false);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> 
            mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"pass123\"}"))
        ).hasCauseInstanceOf(RogueException.class);

        verify(userService, times(1)).validateLogin(any());
        verify(authManager, never()).authenticate(any());
    }

    @Test
    public void updateUserTest() throws Exception {
        when(userService.updateUser(any(UserInput.class))).thenReturn(mock(UserOutput.class));

        mockMvc.perform(put("/auth/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    public void validateTokenTest() throws Exception {
        mockMvc.perform(get("/auth/validate-token"))
                .andExpect(status().isOk());
    }
}
