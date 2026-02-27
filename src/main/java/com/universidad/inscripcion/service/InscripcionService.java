package com.universidad.inscripcion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.universidad.inscripcion.model.Evento;
import com.universidad.inscripcion.model.Participante;
import com.universidad.inscripcion.repository.EventoRepository;
import com.universidad.inscripcion.repository.ParticipanteRepository;

@Service
public class InscripcionService {

    private final EventoRepository eventoRepo;
    private final ParticipanteRepository participanteRepo;

    public InscripcionService(EventoRepository eventoRepo, ParticipanteRepository participanteRepo) {
        this.eventoRepo = eventoRepo;
        this.participanteRepo = participanteRepo;
    }

    public synchronized Participante inscribir(String nombreParticipante, Long eventoId) {
        Evento evento = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("El evento no existe"));

        if (evento.getCuposOcupados() < evento.getCuposMaximos()) {

            evento.setCuposOcupados(evento.getCuposOcupados() + 1);
            eventoRepo.save(evento);

            Participante nuevo = new Participante();
            nuevo.setNombre(nombreParticipante);
            nuevo.setEvento(evento);

            return participanteRepo.save(nuevo);

        } else {
            throw new RuntimeException("No hay cupos disponibles para este evento");
        }
    }

    public List<Participante> listarInscritos(Long eventoId) {
        eventoRepo.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("El evento no existe"));

        return participanteRepo.findAll().stream()
                .filter(p -> p.getEvento().getId().equals(eventoId))
                .collect(Collectors.toList());
    }

    public List<Evento> listarEventos() {
        return eventoRepo.findAll();
    }

    public synchronized void cancelarInscripcion(Long participanteId) {
        Participante participante = participanteRepo.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("El participante no existe"));

        Evento evento = participante.getEvento();

        if (evento.getCuposOcupados() > 0) {
            evento.setCuposOcupados(evento.getCuposOcupados() - 1);
            eventoRepo.save(evento);
        }

        participanteRepo.delete(participante);
        System.out.println(
                "Inscripción cancelada para: " + participante.getNombre() + " en el evento: " + evento.getNombre());
    }
}