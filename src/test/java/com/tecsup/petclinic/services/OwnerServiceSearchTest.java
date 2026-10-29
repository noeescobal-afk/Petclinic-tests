package com.tecsup.petclinic.services;

import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceSearchTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById() {

        Integer ID = 1;
        String FIRST_NAME = "George";
        String LAST_NAME = "Franklin";
        Owner owner = null;

        try {
            owner = ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("" + owner);

        assertEquals(FIRST_NAME, owner.getFirstName());
        assertEquals(LAST_NAME, owner.getLastName());
    }

    @Test
    public void testFindByLastName() {

        String LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        List<Owner> owners = ownerService.findByLastName(LAST_NAME);
        log.info("" + owners);

        assertEquals(SIZE_EXPECTED, owners.size());
        assertEquals(LAST_NAME, owners.get(0).getLastName());
    }

    @Test
    public void testFindByCity() {

        String CITY = "Madison";
        int SIZE_EXPECTED = 4;

        List<Owner> owners = ownerService.findByCity(CITY);
        log.info("" + owners);

        assertEquals(SIZE_EXPECTED, owners.size());
        assertEquals(CITY, owners.get(0).getCity());
    }

}