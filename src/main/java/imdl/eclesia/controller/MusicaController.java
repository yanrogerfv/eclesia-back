package imdl.eclesia.controller;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.input.MusicaInput;
import imdl.eclesia.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import imdl.eclesia.domain.exception.UnauthorizedException;
import imdl.eclesia.service.AppService;

@CrossOrigin
@RestController
@RequestMapping("v1/musicas")
@Tag(name = "v1/Musicas", description = "Inserir, Editar e Listar Musicas")
public class MusicaController {

    private final MusicaService musicaService;
    private final AppService appService;

    public MusicaController(MusicaService musicaService, AppService appService) {
        this.musicaService = musicaService;
        this.appService = appService;
    }

    @GetMapping
    @Operation(summary = "Listagem de todas as músicas.")
    public List<Musica> findAll(){
        return musicaService.listAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma múica pelo seu ID.")
    public Musica findById(@PathVariable UUID id){
        return musicaService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Adiciona uma nova música ao banco.")
    public Musica addMusica(@RequestBody MusicaInput input){
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem adicionar músicas.");
        return musicaService.addMusica(input);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma música.")
    public Musica updateMusica(@PathVariable UUID id, @RequestBody MusicaInput input){
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem editar músicas.");
        return musicaService.updateMusica(id, input);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma música.")
    public void deleteMusica(@PathVariable UUID id){
        if (!appService.isAdminOrLider()) throw new UnauthorizedException("Apenas administradores e líderes podem deletar músicas.");
        musicaService.deleteMusica(id);
    }


}
