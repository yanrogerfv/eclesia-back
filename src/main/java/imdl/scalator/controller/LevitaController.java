package imdl.scalator.controller;

import imdl.scalator.controller.filter.LevitaFilter;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.service.LevitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<Levita> listLevitas(LevitaFilter filter){
        return levitaService.findAll(filter);
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
    public Levita addLevita(@RequestBody LevitaInput input){
        return levitaService.create(input);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza as informaçoes de um levita pelo seu ID.")
    public Levita updateLevita(@PathVariable UUID id, @RequestBody LevitaInput input){
        return levitaService.update(id, input);
    }

    @PatchMapping("/add-instrumento/{id}")
    @Operation(summary = "Adicionar um instrumento à um Levita.")
    public Levita addInstrumento(@PathVariable UUID id, Long inst){
        return levitaService.addInstrumento(id, inst);
    }

    @PatchMapping("/remove-instrumento/{id}")
    @Operation(summary = "Remove um instrumento de um Levita.")
    public Levita removeInstrumento(@PathVariable UUID id, Long inst){
        return levitaService.removeInstrumento(id, inst);
    }

    @GetMapping("/agenda")
    @Operation(summary = "Listagem de Levitas disponíveis para uma data.")
    public List<Levita> listDisponivelInData(@RequestParam LocalDate date){
        return levitaService.findAllDisponivel(date);
    }

    @PatchMapping("/agenda/{id}")
    @Operation(summary = "Adiciona uma data à agenda de um Levita.")
    public Levita addDataInAgenda(@PathVariable UUID id, @RequestParam LocalDate date){
        return levitaService.addDataInAgenda(id, date);
    }

    @PatchMapping("/disponivel/{id}")
    @Operation(summary = "Muda a disponibilidade de um levita pelo seu ID.")
    public Levita changeDisponivel(@PathVariable UUID id){
        return levitaService.changeDisponivel(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um levita.")
    public void deleteLevita(@PathVariable UUID id){
        levitaService.deleteLevita(id);
    }
}
