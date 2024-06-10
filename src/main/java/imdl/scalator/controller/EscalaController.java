package imdl.scalator.controller;

import imdl.scalator.domain.Escala;
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
    public List<Escala> listEscalas(){
        return escalaService.findAllEscalas();
    }

    @GetMapping("/{month}")
    @Operation(summary = "Lista com todas as escalas de um determinado mês.")
    public List<Escala> findByMonth(@PathVariable int month){
        return escalaService.findMonthEscalas(month);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma escala à partir de um ID.")
    public Escala findById(@PathVariable UUID id){
        return escalaService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova escala.")
    public Escala createEscala(@RequestBody EscalaInput input){
        return escalaService.create(input);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma escala.")
    public Escala updateEscala(@PathVariable UUID escalaId, @RequestBody EscalaInput input){
        return escalaService.update(escalaId, input);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma escala.")
    public void deleteMusica(@PathVariable UUID id){
        escalaService.deleteEscala(id);
    }


    @GetMapping("/musicas")
    @Operation(summary = "Lista as músicas em uma escala.")
    public List<Musica> listMusicasInEscal(@RequestParam UUID escalaId){
        return escalaService.findMusicasInEscala(escalaId);
    }

    @PostMapping("/musicas/{id}")
    @Operation(summary = "Adiciona uma música na escala.")
    public Escala addMusicaInEscala(@PathVariable UUID escalaId, @RequestParam UUID musicaId){
        return escalaService.addMusicaInEscala(escalaId, musicaId);
    }

    @DeleteMapping("/musicas/{id}")
    @Operation(summary = "Remove uma música na escala.")
    public Escala removeMusicaInEscala(@PathVariable UUID escalaId, @RequestParam UUID musicaId){
        return escalaService.removeMusicaInEscala(escalaId, musicaId);
    }
}
