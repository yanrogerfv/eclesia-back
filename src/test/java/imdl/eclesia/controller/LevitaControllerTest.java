package imdl.eclesia.controller;

import imdl.eclesia.controller.filter.LevitaFilter;
import imdl.eclesia.domain.Levita;
import imdl.eclesia.domain.LevitaResumed;
import imdl.eclesia.domain.input.LevitaInput;
import imdl.eclesia.service.LevitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LevitaControllerTest {

    @Mock
    private LevitaService levitaService;

    @InjectMocks
    private LevitaController levitaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(levitaController).build();
    }

    @Test
    public void listLevitasTest() throws Exception {
        when(levitaService.findAll(any(LevitaFilter.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/levita"))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).findAll(any(LevitaFilter.class));
    }

    @Test
    public void listResumedLevitasTest() throws Exception {
        when(levitaService.findAllResumed()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/levita/resumed"))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).findAllResumed();
    }

    @Test
    public void findByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(levitaService.findById(id)).thenReturn(mock(Levita.class));

        mockMvc.perform(get("/v1/levita/{id}", id))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).findById(id);
    }

    @Test
    public void findByInstrumentoTest() throws Exception {
        Long instrumentoId = 1L;
        when(levitaService.findAllByInstrument(instrumentoId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/levita/instrumento/{instrumento}", instrumentoId))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).findAllByInstrument(instrumentoId);
    }

    @Test
    public void addLevitaTest() throws Exception {
        when(levitaService.create(any(LevitaInput.class))).thenReturn(mock(Levita.class));

        mockMvc.perform(post("/v1/levita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).create(any());
    }

    @Test
    public void updateLevitaTest() throws Exception {
        when(levitaService.update(any(LevitaInput.class))).thenReturn(mock(Levita.class));

        mockMvc.perform(put("/v1/levita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).update(any());
    }

    @Test
    public void addInstrumentoTest() throws Exception {
        UUID id = UUID.randomUUID();
        Long inst = 1L;
        when(levitaService.addInstrumento(id, inst)).thenReturn(mock(Levita.class));

        mockMvc.perform(patch("/v1/levita/add-instrumento/{id}", id)
                        .param("inst", inst.toString()))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).addInstrumento(id, inst);
    }

    @Test
    public void removeInstrumentoTest() throws Exception {
        UUID id = UUID.randomUUID();
        Long inst = 1L;
        when(levitaService.removeInstrumento(id, inst)).thenReturn(mock(Levita.class));

        mockMvc.perform(patch("/v1/levita/remove-instrumento/{id}", id)
                        .param("inst", inst.toString()))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).removeInstrumento(id, inst);
    }

    @Test
    public void listDisponivelInDataTest() throws Exception {
        LocalDate date = LocalDate.now();
        when(levitaService.findAllDisponivel(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/levita/agenda")
                        .param("date", date.toString()))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).findAllDisponivel(any());
    }

    @Test
    public void getLevitaAgendaTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(levitaService.getLevitaAgenda(id)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/levita/agenda/{id}", id))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).getLevitaAgenda(id);
    }

    @Test
    public void setLevitaAgendaTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(levitaService.setLevitaAgenda(eq(id), anyList())).thenReturn(mock(Levita.class));

        mockMvc.perform(post("/v1/levita/agenda/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).setLevitaAgenda(eq(id), anyList());
    }

    @Test
    public void changeDisponivelTest() throws Exception {
        UUID id = UUID.randomUUID();
        when(levitaService.updateAgentaFromALevita(id)).thenReturn(mock(Levita.class));

        mockMvc.perform(put("/v1/levita/agenda/{id}", id))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).updateAgentaFromALevita(id);
    }

    @Test
    public void deleteLevitaTest() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/v1/levita/{id}", id))
                .andExpect(status().isOk());

        verify(levitaService, times(1)).deleteLevita(id);
    }
}
