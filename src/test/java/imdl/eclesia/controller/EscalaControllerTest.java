package imdl.eclesia.controller;

import imdl.eclesia.domain.Escala;
import imdl.eclesia.domain.EscalaResumed;
import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.exception.PermissionException;
import imdl.eclesia.domain.input.EscalaInput;
import imdl.eclesia.domain.input.MusicasIdsInput;
import imdl.eclesia.service.EscalaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EscalaControllerTest {

    @Mock
    private EscalaService escalaService;

    @InjectMocks
    private EscalaController escalaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(escalaController).build();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockSecurityContext(String role) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        
        doReturn(List.of(authority)).when(authentication).getAuthorities();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void listFullEscalasTest() throws Exception {
        when(escalaService.findAllEscalas(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/escala"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findAllEscalas(null);
    }

    @Test
    public void listResumedEscalasTest() throws Exception {
        when(escalaService.findAllResumidas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/escala/resumed"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findAllResumidas();
    }

    @Test
    public void findByMonthTest() throws Exception {
        when(escalaService.findMonthEscalas(10)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/escala/month/10"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findMonthEscalas(10);
    }

    @Test
    public void findNextEscalasTest() throws Exception {
        when(escalaService.findNextEscalasResumidas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/escala/next"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findNextEscalasResumidas();
    }

    @Test
    public void findByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(escalaService.findById(id)).thenReturn(mock(Escala.class));

        mockMvc.perform(get("/v1/escala/find/{id}", id))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findById(id);
    }

    @Test
    public void createEscalaLiderSuccessTest() throws Exception {
        mockSecurityContext("Líder");
        when(escalaService.create(any(EscalaInput.class))).thenReturn(mock(Escala.class));

        mockMvc.perform(post("/v1/escala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).create(any());
    }

    @Test
    public void createEscalaNormalLevitaThrowsExceptionTest() {
        mockSecurityContext("Levita"); // Not Líder or ADMIN

        assertThatThrownBy(() -> 
            mockMvc.perform(post("/v1/escala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
        ).hasCauseInstanceOf(PermissionException.class);

        verify(escalaService, never()).create(any());
    }

    @Test
    public void updateEscalaAdminSuccessTest() throws Exception {
        mockSecurityContext("ADMIN");
        when(escalaService.update(any(EscalaInput.class))).thenReturn(mock(Escala.class));

        mockMvc.perform(put("/v1/escala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).update(any());
    }

    @Test
    public void updateEscalaNormalLevitaThrowsExceptionTest() {
        mockSecurityContext("Levita");

        assertThatThrownBy(() -> 
            mockMvc.perform(put("/v1/escala")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
        ).hasCauseInstanceOf(PermissionException.class);

        verify(escalaService, never()).update(any());
    }

    @Test
    public void deleteEscalaLiderSuccessTest() throws Exception {
        mockSecurityContext("Líder");
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/v1/escala/{id}", id))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).deleteEscala(id);
    }

    @Test
    public void deleteEscalaNormalLevitaThrowsExceptionTest() {
        mockSecurityContext("Levita");
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> 
            mockMvc.perform(delete("/v1/escala/{id}", id))
        ).hasCauseInstanceOf(PermissionException.class);

        verify(escalaService, never()).deleteEscala(id);
    }

    @Test
    public void cleanEscalasTest() throws Exception {
        mockMvc.perform(delete("/v1/escala/clean"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).cleanEscalas();
    }

    @Test
    public void listMusicasInEscalaTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(escalaService.findMusicasInEscala(id)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/escala/musicas/{id}", id))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).findMusicasInEscala(id);
    }

    @Test
    public void addMusicaInEscalaTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(escalaService.setMusicasInEscala(eq(id), anyList())).thenReturn(mock(Escala.class));

        mockMvc.perform(put("/v1/escala/musicas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"musicasIds\": []}"))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).setMusicasInEscala(eq(id), anyList());
    }

    @Test
    public void removeMusicaInEscalaTest() throws Exception {
        UUID id = UUID.randomUUID();
        UUID musicaId = UUID.randomUUID();
        when(escalaService.removeMusicaInEscala(id, musicaId)).thenReturn(mock(Escala.class));

        mockMvc.perform(delete("/v1/escala/musicas/{id}", id)
                        .param("musicaId", musicaId.toString()))
                .andExpect(status().isOk());

        verify(escalaService, times(1)).removeMusicaInEscala(id, musicaId);
    }
}
