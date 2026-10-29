package com.tecsup.petclinic.owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {
    Owner save(Owner owner);
    void delete(Owner owner);
    Optional<Owner> findById(Integer id);
    List<Owner> findByLastName(String lastName);
    List<Owner> findByCity(String city);
}