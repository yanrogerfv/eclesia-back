package imdl.scalator.controller;

import imdl.scalator.domain.Levita;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.service.LevitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("v1/levita")
@Tag(name = "v1/Levita", description = "Criar, Editar e Listar Levitas")
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

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um levita à partir do seu ID.")
    public Levita findById(@PathVariable UUID id){
        return levitaService.findById(id);
    }

    @GetMapping("/instrumento/{instrumento}")
    @Operation(summary = "Retorna todos os levitas que tocam um determinado instrumento.")
    public List<Levita> findByInstrumento(@PathVariable Long instrumento){
        return levitaService.findAllByInstrument(instrumento);
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um levita.")
    public void deleteLevita(@PathVariable UUID id){
        levitaService.deleteLevita(id);
    }
}
