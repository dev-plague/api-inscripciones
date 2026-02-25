package com.universidad.inscripcion.service;

import com.universidad.inscripcion.model.Evento;
import com.universidad.inscripcion.model.Participante;
import com.universidad.inscripcion.repository.EventoRepository;
import com.universidad.inscripcion.repository.ParticipanteRepository;

import java.util.List;

import org.springframework.stereotype.Service; // Importante añadir esto

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarInscritos'");
    }
}