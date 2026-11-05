package com.tecsup.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    List<Owner> findByLastName(String lastName);
    List<Owner> findByCity(String city);
}
