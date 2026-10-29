package com.tecsup.petclinic.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Owner update(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        ownerRepository.delete(findById(id));
    }

    @Override
    public Owner findById(Integer id) throws OwnerNotFoundException {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Record not found...!"));
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public List<Owner> findByCity(String city) {
        return ownerRepository.findByCity(city);
    }
}