package imdl.eclesia.controller;

import imdl.eclesia.controller.filter.LevitaFilter;
import imdl.eclesia.domain.Levita;
import imdl.eclesia.domain.LevitaResumed;
import imdl.eclesia.domain.input.LevitaInput;
import imdl.eclesia.service.LevitaService;
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
public class  LevitaController {

    private final LevitaService levitaService;

    public LevitaController(LevitaService levitaService) {
        this.levitaService = levitaService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os levitas.")
    public List<Levita> listLevitas(LevitaFilter filter){
        return levitaService.findAll(filter);
    }

    @GetMapping("/resumed")
    @Operation(summary = "Lista todos os levitas.")
    public List<LevitaResumed> listResumedLevitas(){
        return levitaService.findAllResumed();
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

    @PutMapping
    @Operation(summary = "Atualiza as informaçoes de um levita pelo seu ID.")
    public Levita updateLevita(@RequestBody LevitaInput input){
        return levitaService.update(input);
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
    public List<LevitaResumed> listDisponivelInData(@RequestParam LocalDate date){
        return levitaService.findAllDisponivel(date);
    }

    @GetMapping("/agenda/{id}")
    @Operation(summary = "Retorna a agenda de um Levita.")
    public List<LocalDate> getLevitaAgenda(@PathVariable UUID id){
        return levitaService.getLevitaAgenda(id);
    }

    @PostMapping("/agenda/{id}")
    @Operation(summary = "Adiciona uma lista de datas à agenda de um Levita.")
    public Levita setLevitaAgenda(@PathVariable UUID id, @RequestBody List<LocalDate> dates){
        return levitaService.setLevitaAgenda(id, dates);
    }

    @PutMapping("/agenda/{id}")
    @Operation(summary = "Atualiza a agenda de um Levita.")
    public Levita changeDisponivel(@PathVariable UUID id){
        return levitaService.updateAgentaFromALevita(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um levita.")
    public void deleteLevita(@PathVariable UUID id){
        levitaService.deleteLevita(id);
    }
}
