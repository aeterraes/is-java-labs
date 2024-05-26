package org.example.service.services;

import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.entity.Owner;

import java.util.List;

public interface OwnerService {
    void saveOwner(Owner owner);

    Owner getOwnerById(int id);

    List<Owner> getAllOwners();

    void updateOwner(Owner owner);

    void deleteOwner(int id);

    List<Cat> findAllByOwnerId(int id);

}
