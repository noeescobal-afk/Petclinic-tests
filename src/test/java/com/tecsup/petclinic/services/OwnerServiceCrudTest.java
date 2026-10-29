package com.tecsup.petclinic.services;

import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerRepository;
import com.tecsup.petclinic.owner.OwnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceCrudTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void testCreateOwner() {
        Owner newOwner = new Owner(null, "Noe", "Escobal", "Av. Lima 123", "Lima", "999111222");
        Owner savedOwner = new Owner(11, "Noe", "Escobal", "Av. Lima 123", "Lima", "999111222");

        when(ownerRepository.save(newOwner)).thenReturn(savedOwner);

        Owner result = ownerService.create(newOwner);

        assertNotNull(result.getId());
        assertEquals(11, result.getId());
        assertEquals("Noe", result.getFirstName());
        assertEquals("Escobal", result.getLastName());
        assertEquals("Lima", result.getCity());
        verify(ownerRepository, times(1)).save(newOwner);
    }

    @Test
    void testUpdateOwner() {
        Owner updated = new Owner(1, "Noe", "Escobal", "Av. Arequipa 456", "Arequipa", "988777666");

        when(ownerRepository.save(any(Owner.class))).thenAnswer(inv -> inv.getArgument(0));

        Owner result = ownerService.update(updated);

        ArgumentCaptor<Owner> captor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(captor.capture());

        assertEquals(1, result.getId());
        assertEquals("Arequipa", result.getCity());
        assertEquals("Av. Arequipa 456", result.getAddress());
        assertEquals("988777666", result.getTelephone());
        assertEquals("Arequipa", captor.getValue().getCity());
    }

    @Test
    void testDeleteOwner() throws OwnerNotFoundException {
        Owner existing = new Owner(1, "Noe", "Escobal", "Av. Lima 123", "Lima", "999111222");

        when(ownerRepository.findById(1)).thenReturn(Optional.of(existing));
        doNothing().when(ownerRepository).delete(existing);

        ownerService.delete(1);

        verify(ownerRepository, times(1)).findById(1);
        verify(ownerRepository, times(1)).delete(existing);
    }
}