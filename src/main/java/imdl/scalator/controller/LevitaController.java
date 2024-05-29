package imdl.scalator.controller;

import imdl.scalator.domain.Levita;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.service.LevitaService;
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
    public List<Levita> listLevitas(){
        return levitaService.findAll();
    }
    @PostMapping
    public Levita addLevita(LevitaInput input){
        return levitaService.create(input);
    }
    @PutMapping("/{id}")
    public Levita updateLevita(@PathVariable UUID id, @RequestBody LevitaInput input){
        return levitaService.update(id, input);
    }
    @PatchMapping
    public Levita changeDisponivel(@RequestBody UUID id){
        return levitaService.changeDisponivel(id);
    }
}
