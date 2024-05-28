package imdl.scalator.controller;

import imdl.scalator.domain.Escala;
import imdl.scalator.service.EscalaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("v1/escala")
public class EscalaController {

    private final EscalaService escalaService;

    public EscalaController(EscalaService escalaService) {
        this.escalaService = escalaService;
    }

    @GetMapping
    public List<Escala> listEscalas(){
        return escalaService.findAllEscalas();
    }

    @GetMapping("/{mes}")
    public List<Escala> listInPeriodo(){
        return escalaService.findMonthEscalas();
    }

}
