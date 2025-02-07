package imdl.eclesia.controller;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.input.InstrumentoInput;
import imdl.eclesia.service.InstrumentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("v1/instrumento")
@Tag(name = "v1/Instrumento", description = "Criar e Listar Instrumentos")
public class InstrumentoController {

    private final InstrumentoService instrumentoService;

    public InstrumentoController(InstrumentoService instrumentoService) {
        this.instrumentoService = instrumentoService;
    }

    @GetMapping
    public List<Instrumento> listInstrumentos(){
        return instrumentoService.findAll();
    }

    @GetMapping("/{id}")
    public Instrumento findById(@PathVariable Long id){
        return instrumentoService.findById(id);
    }

    @PostMapping
    public Instrumento createInstrumento(@RequestBody InstrumentoInput input){
        return instrumentoService.createInstrumento(input);
    }

    @DeleteMapping("/{id}")
    public void deleteInstrumento(@PathVariable Long id){
        instrumentoService.deleteInstrumento(id);
    }

}
