package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.OwnerDTO;

public class OwnerTObjectCreator {

    public static OwnerDTO getOwnerTO() {
        OwnerDTO ownerTO = new OwnerDTO();
        ownerTO.setFirstName("Ivan");
        ownerTO.setLastName("Jara");
        ownerTO.setAddress("Av. Lima 123");
        ownerTO.setCity("Lima");
        ownerTO.setTelephone("987654321");
        return ownerTO;
    }
}
