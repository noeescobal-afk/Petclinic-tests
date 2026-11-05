package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.mappers.OwnerMapper;
import com.tecsup.petclinic.owner.Owner;
import com.tecsup.petclinic.owner.OwnerNotFoundException;
import com.tecsup.petclinic.owner.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper mapper;

    public OwnerController(OwnerService ownerService, OwnerMapper mapper) {
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/owners")
    ResponseEntity<List<OwnerDTO>> findAll(@RequestParam(required = false) String lastName) {
        List<Owner> owners = (lastName == null)
                ? ownerService.findAll()
                : ownerService.findByLastName(lastName);
        return ResponseEntity.ok(owners.stream().map(mapper::mapToDto).toList());
    }

    @PostMapping(value = "/owners")
    ResponseEntity<OwnerDTO> create(@Valid @RequestBody OwnerDTO ownerTO) {
        Owner owner = mapper.mapToEntity(ownerTO);
        owner.setId(null);
        Owner newOwner = ownerService.create(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(newOwner));
    }

    @GetMapping(value = "/owners/{id}")
    ResponseEntity<OwnerDTO> findById(@PathVariable Integer id) {
        try {
            Owner owner = ownerService.findById(id);
            return ResponseEntity.ok(mapper.mapToDto(owner));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/owners/{id}")
    ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerTO, @PathVariable Integer id) {
        try {
            Owner owner = ownerService.findById(id);
            owner.setFirstName(ownerTO.getFirstName());
            owner.setLastName(ownerTO.getLastName());
            owner.setAddress(ownerTO.getAddress());
            owner.setCity(ownerTO.getCity());
            owner.setTelephone(ownerTO.getTelephone());
            Owner updatedOwner = ownerService.update(owner);
            return ResponseEntity.ok(mapper.mapToDto(updatedOwner));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/owners/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}