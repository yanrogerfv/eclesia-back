package imdl.eclesia.controller;

import imdl.eclesia.domain.Escala;
import imdl.eclesia.domain.EscalaResumed;
import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.exception.PermissionException;
import imdl.eclesia.domain.input.EscalaInput;
import imdl.eclesia.domain.input.MusicasIdsInput;
import imdl.eclesia.service.EscalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import imdl.eclesia.service.AppService;

@CrossOrigin
@RestController
@RequestMapping("v1/escala")
@Tag(name = "v1/Escala", description = "Criar, Editar e Listar Escalas")
public class EscalaController {

    private final EscalaService escalaService;
    private final AppService appService;

    public EscalaController(EscalaService escalaService, AppService appService) {
        this.escalaService = escalaService;
        this.appService = appService;
    }

    @GetMapping
    @Operation(summary = "Lista com todas as escalas.")
    public List<Escala> listFullEscalas(@RequestParam(required = false) UUID levita){
        return escalaService.findAllEscalas(levita);
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

    @GetMapping("/find/{escalaId}")
    @Operation(summary = "Retorna uma escala à partir de um ID.")
    public Escala findById(@PathVariable UUID escalaId){
        return escalaService.findById(escalaId);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova escala.")
    public Escala createEscala(@RequestBody EscalaInput input){
        if (!appService.isAdminOrLider())
            throw new PermissionException("Usuário não possui permissão para criar uma escala.");
        return escalaService.create(input);
    }

    @PutMapping
    @Operation(summary = "Atualiza uma escala.")
    public Escala updateEscala(@RequestBody EscalaInput input){
        if (!appService.isAdminOrLider())
            throw new PermissionException("Usuário não possui permissão para editar uma escala.");
        return escalaService.update(input);
    }

    @DeleteMapping("/{escalaId}")
    @Operation(summary = "Deletar uma escala.")
    public void deleteEscala(@PathVariable UUID escalaId){
        if (!appService.isAdminOrLider())
            throw new PermissionException("Usuário não possui permissão para deletar uma escala.");
        escalaService.deleteEscala(escalaId);
    }

    @DeleteMapping("/clean")
    @Operation(summary = "Deletar escalas antigas.")
    public void cleanEscalas(){
        if (!appService.isAdmin())
            throw new PermissionException("Usuário não possui permissão para limpar escalas.");
        escalaService.cleanEscalas();
    }

    //Músicas na Escala
    @GetMapping("/musicas/{escalaId}")
    @Operation(summary = "Lista as músicas em uma escala.")
    public List<Musica> listMusicasInEscala(@PathVariable UUID escalaId){
        return escalaService.findMusicasInEscala(escalaId);
    }

    @PutMapping("/musicas/{escalaId}")
    @Operation(summary = "Adiciona uma música na escala.")
    public Escala addMusicaInEscala(@PathVariable UUID escalaId, @RequestBody MusicasIdsInput musicasIds){
        if (!appService.isAdminOrLider())
            throw new PermissionException("Usuário não possui permissão para alterar músicas da escala.");
        return escalaService.setMusicasInEscala(escalaId, musicasIds.getMusicasIds());
    }

    @DeleteMapping("/musicas/{escalaId}")
    @Operation(summary = "Remove uma música na escala.")
    public Escala removeMusicaInEscala(@PathVariable UUID escalaId, @RequestParam UUID musicaId){
        if (!appService.isAdminOrLider())
            throw new PermissionException("Usuário não possui permissão para alterar músicas da escala.");
        return escalaService.removeMusicaInEscala(escalaId, musicaId);
    }
}
