package imdl.eclesia.controller;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.input.InstrumentoInput;
import imdl.eclesia.service.InstrumentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import imdl.eclesia.domain.exception.UnauthorizedException;
import imdl.eclesia.service.AppService;

@CrossOrigin
@RestController
@RequestMapping("v1/instrumento")
@Tag(name = "v1/Instrumento", description = "Criar e Listar Instrumentos")
public class InstrumentoController {

    private final InstrumentoService instrumentoService;
    private final AppService appService;

    public InstrumentoController(InstrumentoService instrumentoService, AppService appService) {
        this.instrumentoService = instrumentoService;
        this.appService = appService;
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
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem criar instrumentos.");
        return instrumentoService.createInstrumento(input);
    }

    @DeleteMapping("/{id}")
    public void deleteInstrumento(@PathVariable Long id){
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem remover instrumentos.");
        instrumentoService.deleteInstrumento(id);
    }

}
