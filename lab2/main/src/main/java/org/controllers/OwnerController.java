package org.controllers;

import org.model.Owner;
import org.services.OwnerService;

import java.time.LocalDate;

public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public void createOwner(String name, LocalDate birthDate) {
        Owner owner = new Owner(name, birthDate);
        ownerService.saveOwner(owner);
    }

    public void getOwnerById(int id) {
        Owner owner = ownerService.getOwnerById(id);

    }

    public void updateOwner(int id, String name, LocalDate birthDate) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner != null) {
            owner.setName(name);
            owner.setBirthDate(birthDate);
            ownerService.updateOwner(owner);
        }
    }

    public void deleteOwner(int id) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner != null) {
            ownerService.deleteOwner(owner);
        }
    }
}
