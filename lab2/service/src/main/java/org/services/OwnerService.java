package org.services;

import org.dao.OwnerDao;
import org.model.Owner;
public class OwnerService {

    private final OwnerDao ownerDao;

    public OwnerService(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    public void saveOwner(Owner owner) {
        ownerDao.saveOwner(owner);
    }

    public Owner getOwnerById(int id) {
        return ownerDao.getOwnerById(id);
    }

    public void updateOwner(Owner owner) {
        ownerDao.updateOwner(owner);
    }

    public void deleteOwner(Owner owner) {
        ownerDao.deleteOwner(owner);
    }
}
