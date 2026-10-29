package com.tecsup.petclinic.services;

import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceCrudTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testCreateOwner() {

        Owner owner = new Owner(null, "Noe", "Escobal", "Av. Lima 123", "Lima", "999111222");

        Owner created = this.ownerService.create(owner);
        log.info("OWNER CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("Noe", created.getFirstName());
        assertEquals("Escobal", created.getLastName());
        assertEquals("Lima", created.getCity());
    }

    @Test
    public void testUpdateOwner() throws OwnerNotFoundException {

        Owner created = this.ownerService.create(
                new Owner(null, "Maria", "Lopez", "Av. Sol 100", "Cusco", "955444333"));

        created.setAddress("Av. Arequipa 456");
        created.setCity("Arequipa");
        created.setTelephone("988777666");

        Owner updated = this.ownerService.update(created);

        Owner fromDb = this.ownerService.findById(updated.getId());

        assertEquals(created.getId(), fromDb.getId());
        assertEquals("Av. Arequipa 456", fromDb.getAddress());
        assertEquals("Arequipa", fromDb.getCity());
        assertEquals("988777666", fromDb.getTelephone());
    }

    @Test
    public void testDeleteOwner() throws OwnerNotFoundException {

        Owner created = this.ownerService.create(
                new Owner(null, "Juan", "Perez", "Jr. Union 50", "Lima", "911222333"));
        Integer id = created.getId();

        this.ownerService.delete(id);

        assertThrows(OwnerNotFoundException.class, () -> this.ownerService.findById(id));
    }
}