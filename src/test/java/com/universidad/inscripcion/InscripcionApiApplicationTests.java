package com.universidad.inscripcion;

import static org.junit.jupiter.api.Assertions.*;
import com.universidad.inscripcion.model.Evento;
import com.universidad.inscripcion.repository.EventoRepository;
import com.universidad.inscripcion.service.InscripcionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InscripcionServiceTest {

	@Autowired
	private InscripcionService inscripcionService;

	@Autowired
	private EventoRepository eventoRepo;

	@Test
	void noInscribirSiNoHayCupos() {
		// evento con 0 cupos
		Evento eventoSinCupo = new Evento();
		eventoSinCupo.setNombre("Evento Lleno");
		eventoSinCupo.setCuposMaximos(0);
		eventoSinCupo.setCuposOcupados(0);
		eventoSinCupo = eventoRepo.save(eventoSinCupo);

		// 2. Intentar inscribir y verificar si lanza la excepcion
		Long id = eventoSinCupo.getId();
		assertThrows(RuntimeException.class, () -> {
			inscripcionService.inscribir("Juan Perez", id);
		}, "Debería haber lanzado una excepción por falta de cupos");
	}
}