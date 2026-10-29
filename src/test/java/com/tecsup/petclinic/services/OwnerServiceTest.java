
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	// INTEGRANTE C - CASOS DE ERROR

	// PRUEBA 1: Buscar propietario inexistente
	@Test
	public void testFindOwnerById_NotFound() {

		Integer id = 999999;

		assertThrows(
				OwnerNotFoundException.class,
				() -> ownerService.findById(id)
		);
	}

	// PRUEBA 2: Eliminar propietario inexistente
	@Test
	public void testDeleteOwner_NotFound() {

		Integer id = 999999;

		assertThrows(
				OwnerNotFoundException.class,
				() -> ownerService.delete(id)
		);
	}

	// PRUEBA 3: Buscar apellido inexistente
	@Test
	public void testFindByLastName_Empty() {

		String apellido = "ApellidoInexistente999";

		List<?> propietarios =
				ownerService.findByLastName(apellido);

		assertNotNull(propietarios);

		assertTrue(
				propietarios.isEmpty(),
				"La lista debe estar vacia"
		);
	}
}
