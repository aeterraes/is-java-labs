package org.example.service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private final CatRepository catRepository;

    @Override
    public void saveCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    public Cat getCatById(int id) {
        return catRepository.findById(id).orElse(null);
    }
    @Override
    public Cat getCatByName(String name){ return catRepository.findCatByName(name);}

    @Override
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    @Override
    public void updateCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    public void deleteCat(int id) {
        catRepository.deleteById(id);
    }

    @Override
    public List<Cat> getCatsByColor(String color) {
        return catRepository.findAllByColor(color);
    }

    @Override
    public List<Cat> getCatsByBreed(String breed) {
        return catRepository.findAllByBreed(breed);
    }
}

