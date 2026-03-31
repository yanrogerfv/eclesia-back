package imdl.eclesia.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import imdl.eclesia.auth.controller.input.UserInput;
import imdl.eclesia.auth.controller.output.UserOutput;
import imdl.eclesia.auth.dto.CreateUserOutput;
import imdl.eclesia.auth.dto.RoleDTO;
import imdl.eclesia.auth.service.RoleService;
import imdl.eclesia.auth.service.UserService;
import imdl.eclesia.domain.LevitaResumed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void listUsersTest() throws Exception {
        when(userService.list()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/auth/user"))
                .andExpect(status().isOk());

        verify(userService, times(1)).list();
    }

    @Test
    public void activeTest() throws Exception {
        when(userService.activeUser()).thenReturn(mock(UserOutput.class));

        mockMvc.perform(get("/auth/user/active"))
                .andExpect(status().isOk());

        verify(userService, times(1)).activeUser();
    }

    @Test
    public void listInactiveUsersTest() throws Exception {
        when(userService.listAllNotActive()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/auth/user/inactive"))
                .andExpect(status().isOk());

        verify(userService, times(1)).listAllNotActive();
    }

    @Test
    public void createUserTest() throws Exception {
        UserInput input = mock(UserInput.class);
        CreateUserOutput output = mock(CreateUserOutput.class);
        when(userService.createUserNotActive(any())).thenReturn(output);

        mockMvc.perform(post("/auth/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).createUserNotActive(any());
    }

    @Test
    public void restoreUserTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(patch("/auth/user/restore/{id}", id))
                .andExpect(status().isOk());

        verify(userService, times(1)).restore(id);
    }

    @Test
    public void deleteUserTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/auth/user/{id}", id))
                .andExpect(status().isOk());

        verify(userService, times(1)).remove(id);
    }

    @Test
    public void listLevitasWithoutLoginTest() throws Exception {
        when(userService.listLevitasWithoutLogin()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/auth/user/levita-x"))
                .andExpect(status().isOk());

        verify(userService, times(1)).listLevitasWithoutLogin();
    }

    @Test
    public void listRolesTest() throws Exception {
        when(roleService.list()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/auth/role"))
                .andExpect(status().isOk());

        verify(roleService, times(1)).list();
    }

    @Test
    public void listRolesOfUserTest() throws Exception {
        String username = "user1";
        UserOutput mockedUser = mock(UserOutput.class);
        when(mockedUser.getRole()).thenReturn(mock(RoleDTO.class));
        when(userService.findByUsername(username)).thenReturn(mockedUser);

        mockMvc.perform(get("/auth/role/{username}", username))
                .andExpect(status().isOk());

        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    public void createRoleTest() throws Exception {
        RoleDTO dto = mock(RoleDTO.class);
        when(roleService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/auth/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(roleService, times(1)).create(any());
    }

    @Test
    public void updateRoleTest() throws Exception {
        RoleDTO dto = mock(RoleDTO.class);
        when(roleService.edit(any())).thenReturn(dto);

        mockMvc.perform(put("/auth/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(roleService, times(1)).edit(any());
    }

    @Test
    public void deleteRoleTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/auth/role/{id}", id).param("id", id.toString()))
                .andExpect(status().isOk());

        verify(roleService, times(1)).remove(id);
    }

    @Test
    public void getNewCodeForUserTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(userService.generateNewAccessCode(id)).thenReturn("12345");

        mockMvc.perform(get("/auth/recover").param("userId", id.toString()))
                .andExpect(status().isOk());

        verify(userService, times(1)).generateNewAccessCode(id);
    }
}
