package org.services;

import org.dao.CatDao;
import org.model.Cat;

public class CatService {
    private final CatDao catDao;

    public CatService(CatDao catDao) {
        this.catDao = catDao;
    }

    public void saveCat(Cat cat) {
        catDao.saveCat(cat);
    }

    public Cat getCatById(int id) {
        return catDao.getCatById(id);
    }

    public Cat getCatByName(String name) {
        return catDao.getCatByName(name);
    }

    public void updateCat(Cat cat) {
        catDao.updateCat(cat);
    }

    public void deleteCat(Cat cat) {
        catDao.deleteCat(cat);
    }

    public void addFriendToCat(Cat cat, Cat friend) {
        catDao.addFriendToCat(cat, friend);
    }
}