package org.example.service.services;


import org.example.dataaccess.entity.Cat;

import java.util.List;
public interface CatService {
    void saveCat(Cat cat);

    Cat getCatById(int id);

    Cat getCatByName(String name);

    List<Cat> getAllCats();

    void updateCat(Cat cat);

    void deleteCat(int id);

    List<Cat> getCatsByColor(String color);

    List<Cat> getCatsByBreed(String breed);
}
