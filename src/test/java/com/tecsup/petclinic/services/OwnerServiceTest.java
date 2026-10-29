
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerService;

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
	// Ivan Jara Ayala

	// PRUEBA 1: Buscar propietario inexistente
	@Test
	public void testFindOwnerById_NotFound() {

		Integer id = 999999;

		assertThrows(
				OwnerNotFoundException.class,
				() -> ownerService.findById(id),
				"Debe lanzar OwnerNotFoundException"
		);
	}

	// PRUEBA 2: Eliminar propietario inexistente
	@Test
	public void testDeleteOwner_NotFound() {

		Integer id = 999999;

		assertThrows(
				OwnerNotFoundException.class,
				() -> ownerService.delete(id),
				"Debe lanzar excepcion al eliminar un dueño inexistente"
		);
	}

	// PRUEBA 3: Buscar apellido sin coincidencias
	@Test
	public void testFindByLastName_Empty() {

		String apellido = "ApellidoInexistente999";

		List<Owner> propietarios =
				ownerService.findByLastName(apellido);

		assertNotNull(
				propietarios,
				"La lista no debe ser null"
		);

		assertTrue(
				propietarios.isEmpty(),
				"Debe devolver una lista vacia"
		);
	}
}
