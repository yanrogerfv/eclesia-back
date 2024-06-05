package imdl.scalator.controller;

import imdl.scalator.domain.Levita;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.service.LevitaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("v1/levita")
public class LevitaController {

    private final LevitaService levitaService;

    public LevitaController(LevitaService levitaService) {
        this.levitaService = levitaService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os levitas.")
    public List<Levita> listLevitas(){
        return levitaService.findAll();
    }

    @PostMapping
    @Operation(summary = "Adiciona um novo levita ao banco.")
    public Levita addLevita(LevitaInput input){
        return levitaService.create(input);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza as informaçoes de um levita pelo seu ID.")
    public Levita updateLevita(@PathVariable UUID id, @RequestBody LevitaInput input){
        return levitaService.update(id, input);
    }

    @PatchMapping
    @Operation(summary = "Muda a disponibilidade de um levita pelo seu ID.")
    public Levita changeDisponivel(@RequestBody UUID id){
        return levitaService.changeDisponivel(id);
    }
}
