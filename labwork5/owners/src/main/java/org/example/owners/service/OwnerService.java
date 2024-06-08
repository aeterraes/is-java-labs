package org.example.owners.service;

import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.entity.Owner;
import org.example.dataaccess.repository.CatRepository;
import org.example.dataaccess.repository.OwnerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class OwnerService {
@Autowired
    private OwnerRepository ownerRepository;
@Autowired
    private CatRepository catRepository;

    @RabbitListener(queues = "saveOwnerQueue")
    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @RabbitListener(queues = "getOwnerByIdQueue")
    public Owner getOwnerById(int id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @RabbitListener(queues = "getAllOwnersQueue")
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @RabbitListener(queues = "updateOwnerQueue")
    public void updateOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @RabbitListener(queues = "deleteOwnerQueue")
    public void deleteOwner(int id) {
        ownerRepository.deleteById(id);
    }

    public List<Cat> findAllByOwnerId(int id) {
        return catRepository.findAllByOwnerId(id);
    }
}
