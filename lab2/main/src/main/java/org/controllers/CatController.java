package org.controllers;

import org.model.Cat;
import org.services.CatService;

import java.time.LocalDate;

public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    public void createCat(String name, LocalDate birthDate, String breed, String color, int ownerId) {
        Cat cat = new Cat(name, birthDate, breed, color, ownerId);
        catService.saveCat(cat);
    }

    public void getCatById(int id) {
        Cat cat = catService.getCatById(id);
    }

    public void updateCat(int id, String name, String breed, String color) {
        Cat cat = catService.getCatById(id);
        cat.setName(name);
        cat.setBreed(breed);
        cat.setColor(color);
        catService.updateCat(cat);
    }

    public void deleteCat(int id) {
        Cat cat = catService.getCatById(id);
        catService.deleteCat(cat);
    }

    public void addFriendToCat(int catId, int friendId) {
        Cat cat = catService.getCatById(catId);
        Cat friend = catService.getCatById(friendId);
        catService.addFriendToCat(cat, friend);
    }
}