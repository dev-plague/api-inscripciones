package com.universidad.inscripcion.repository;

import com.universidad.inscripcion.model.Evento; // Importa Evento, no Participante
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // JpaRepository<Evento, Long> significa:
    // "Maneja la entidad Evento, cuya llave primaria (@Id) es de tipo Long"

}