package org.example.service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.entity.Owner;
import org.example.dataaccess.repository.CatRepository;
import org.example.dataaccess.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class OwnerServiceImpl implements OwnerService {

@Autowired
private final OwnerRepository ownerRepository;
@Autowired
private final CatRepository catRepository;

    @Override
    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public Owner getOwnerById(int id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(int id) {
        ownerRepository.deleteById(id);
    }
    public List<Cat> findAllByOwnerId(int id) {

        return catRepository.findAllByOwnerId(id);
    }
}

