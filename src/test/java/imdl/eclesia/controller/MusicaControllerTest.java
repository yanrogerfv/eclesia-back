package imdl.eclesia.controller;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.input.MusicaInput;
import imdl.eclesia.service.MusicaService;
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
public class MusicaControllerTest {

    @Mock
    private MusicaService musicaService;

    @InjectMocks
    private MusicaController musicaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(musicaController).build();
    }

    @Test
    public void findAllTest() throws Exception {
        when(musicaService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/musicas"))
                .andExpect(status().isOk());

        verify(musicaService, times(1)).listAll();
    }

    @Test
    public void findByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(musicaService.findById(id)).thenReturn(mock(Musica.class));

        mockMvc.perform(get("/v1/musicas/{id}", id))
                .andExpect(status().isOk());

        verify(musicaService, times(1)).findById(id);
    }

    @Test
    public void addMusicaTest() throws Exception {
        when(musicaService.addMusica(any(MusicaInput.class))).thenReturn(mock(Musica.class));

        mockMvc.perform(post("/v1/musicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(musicaService, times(1)).addMusica(any());
    }

    @Test
    public void updateMusicaTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(musicaService.updateMusica(eq(id), any(MusicaInput.class))).thenReturn(mock(Musica.class));

        mockMvc.perform(put("/v1/musicas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(musicaService, times(1)).updateMusica(eq(id), any());
    }

    @Test
    public void deleteMusicaTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/v1/musicas/{id}", id))
                .andExpect(status().isOk());

        verify(musicaService, times(1)).deleteMusica(id);
    }
}
