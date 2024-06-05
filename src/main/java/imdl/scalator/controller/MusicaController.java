package imdl.scalator.controller;

import imdl.scalator.domain.Musica;
import imdl.scalator.domain.input.MusicaInput;
import imdl.scalator.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("v1/musicas")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @GetMapping
    @Operation(summary = "Listagem de todas as músicas.")
    public List<Musica> findAll(){
        return musicaService.listAll();
    }

    @PostMapping
    @Operation(summary = "Adiciona uma nova música ao banco.")
    public Musica addMusica(@RequestBody MusicaInput input){
        return musicaService.addMusica(input);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma música.")
    public Musica updateMusica(@PathVariable UUID id, @RequestBody MusicaInput input){
        return musicaService.updateMusica(id, input);
    }
}
