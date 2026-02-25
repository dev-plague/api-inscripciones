package com.universidad.inscripcion.controller;

import com.universidad.inscripcion.model.Evento;
import com.universidad.inscripcion.model.Participante;
import com.universidad.inscripcion.repository.EventoRepository;
import com.universidad.inscripcion.service.InscripcionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones") // http://localhost:8080/api/inscripciones
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final EventoRepository eventoRepo;

    public InscripcionController(InscripcionService inscripcionService, EventoRepository eventoRepo) {
        this.inscripcionService = inscripcionService;
        this.eventoRepo = eventoRepo;
    }

    @PostMapping("/registrar")
    public Participante registrar(@RequestParam String nombre, @RequestParam Long eventoId) {
        return inscripcionService.inscribir(nombre, eventoId);
    }

    @PostMapping("/eventos")
    public Evento crearEvento(@RequestParam String nombre, @RequestParam int cupos) {
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(nombre);
        nuevoEvento.setCuposMaximos(cupos);
        nuevoEvento.setCuposOcupados(0);

        return eventoRepo.save(nuevoEvento);
    }

    @GetMapping("/evento/{eventoId}")
    public List<Participante> listarPorEvento(@PathVariable Long eventoId) {
        return inscripcionService.listarInscritos(eventoId);
    }
}