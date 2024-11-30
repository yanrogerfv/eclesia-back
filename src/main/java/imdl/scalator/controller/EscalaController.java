package imdl.scalator.controller;

import imdl.scalator.domain.Escala;
import imdl.scalator.domain.EscalaResumed;
import imdl.scalator.domain.Musica;
import imdl.scalator.domain.input.EscalaInput;
import imdl.scalator.service.EscalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("v1/escala")
@Tag(name = "v1/Escala", description = "Criar, Editar e Listar Escalas")
public class EscalaController {

    private final EscalaService escalaService;

    public EscalaController(EscalaService escalaService) {
        this.escalaService = escalaService;
    }

    @GetMapping
    @Operation(summary = "Lista com todas as escalas.")
    public List<Escala> listFullEscalas(){
        return escalaService.findAllEscalas();
    }
    @GetMapping("/resumed")
    @Operation(summary = "Lista com todas as escalas.")
    public List<EscalaResumed> listResumedEscalas(){
        return escalaService.findAllResumidas();
    }

    @GetMapping("/month/{month}")
    @Operation(summary = "Lista com todas as escalas de um determinado mês.")
    public List<Escala> findByMonth(@PathVariable int month){
        return escalaService.findMonthEscalas(month);
    }

    @GetMapping("/next")
    @Operation(summary = "Lista com as próximas escalas.")
    public List<EscalaResumed> findNextEscalas(){
        return escalaService.findNextEscalasResumidas();
    }

    @GetMapping("/{escalaId}")
    @Operation(summary = "Retorna uma escala à partir de um ID.")
    public Escala findById(@PathVariable UUID escalaId){
        return escalaService.findById(escalaId);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova escala.")
    public Escala createEscala(@RequestBody EscalaInput input){
        return escalaService.create(input);
    }

    @PutMapping("/{escalaId}")
    @Operation(summary = "Atualiza uma escala.")
    public Escala updateEscala(@PathVariable UUID escalaId, @RequestBody EscalaInput input){
        return escalaService.update(escalaId, input);
    }

    @DeleteMapping("/{escalaId}")
    @Operation(summary = "Deletar uma escala.")
    public void deleteEscala(@PathVariable UUID escalaId){
        escalaService.deleteEscala(escalaId);
    }

    //Músicas na Escala
    @GetMapping("/musicas")
    @Operation(summary = "Lista as músicas em uma escala.")
    public List<Musica> listMusicasInEscal(@RequestParam UUID escalaId){
        return escalaService.findMusicasInEscala(escalaId);
    }

    @PostMapping("/musicas/{escalaId}")
    @Operation(summary = "Adiciona uma música na escala.")
    public Escala addMusicaInEscala(@PathVariable UUID escalaId, @RequestParam UUID musicaId){
        return escalaService.addMusicaInEscala(escalaId, musicaId);
    }

    @DeleteMapping("/musicas/{escalaId}")
    @Operation(summary = "Remove uma música na escala.")
    public Escala removeMusicaInEscala(@PathVariable UUID escalaId, @RequestParam UUID musicaId){
        return escalaService.removeMusicaInEscala(escalaId, musicaId);
    }
}
