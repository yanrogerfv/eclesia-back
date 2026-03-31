package imdl.eclesia.controller;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.input.InstrumentoInput;
import imdl.eclesia.service.InstrumentoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InstrumentoControllerTest {

    @Mock
    private InstrumentoService instrumentoService;

    @InjectMocks
    private InstrumentoController instrumentoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(instrumentoController).build();
    }

    @Test
    public void listInstrumentosTest() throws Exception {
        when(instrumentoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/instrumento"))
                .andExpect(status().isOk());

        verify(instrumentoService, times(1)).findAll();
    }

    @Test
    public void findByIdTest() throws Exception {
        Long id = 1L;
        when(instrumentoService.findById(id)).thenReturn(mock(Instrumento.class));

        mockMvc.perform(get("/v1/instrumento/{id}", id))
                .andExpect(status().isOk());

        verify(instrumentoService, times(1)).findById(id);
    }

    @Test
    public void createInstrumentoTest() throws Exception {
        when(instrumentoService.createInstrumento(any(InstrumentoInput.class))).thenReturn(mock(Instrumento.class));

        mockMvc.perform(post("/v1/instrumento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(instrumentoService, times(1)).createInstrumento(any());
    }

    @Test
    public void deleteInstrumentoTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/v1/instrumento/{id}", id))
                .andExpect(status().isOk());

        verify(instrumentoService, times(1)).deleteInstrumento(id);
    }
}
