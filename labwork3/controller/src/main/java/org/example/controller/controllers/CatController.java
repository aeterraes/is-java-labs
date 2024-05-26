package org.example.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.CatDto;
import org.example.dataaccess.entity.Cat;
import org.example.service.services.CatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor

public class CatController {

    @Autowired
    private final CatService catService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public List<CatDto> getAllCats() {
        return catService.getAllCats().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void saveCat(@RequestBody CatDto catDto) {
        Cat cat = convertToEntity(catDto);
        catService.saveCat(cat);
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable int id) {
        Cat cat = catService.getCatById(id);
        return cat != null ? convertToDto(cat) : null;
    }
    @GetMapping("/{name}")
    public CatDto getCatByName(@PathVariable String name) {
        Cat cat = catService.getCatByName(name);
        return cat != null ? convertToDto(cat) : null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCat(@PathVariable("id") int id) {
        catService.deleteCat(id);
    }

    @PutMapping("/update/{id}")
    public CatDto updateCat(@PathVariable("id") int id, @RequestBody CatDto catDto) {
        Cat cat = catService.getCatById(id);
        if (cat != null) {
            cat.setName(catDto.getName());
            cat.setBirthDate(catDto.getBirthDate());
            cat.setBreed(catDto.getBreed());
            cat.setColor(catDto.getColor());
            catService.updateCat(cat);
            return convertToDto(cat);
        } else {
            return null;
        }
    }

    @GetMapping("/color/{color}")
    public List<CatDto> getCatsByColor(@PathVariable("color") String color) {
        return catService.getCatsByColor(color).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/breed/{breed}")
    public List<CatDto> getCatsByBreed(@PathVariable("breed") String breed) {
        return catService.getCatsByBreed(breed).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CatDto convertToDto(Cat cat) {
        return modelMapper.map(cat, CatDto.class);
    }

    private Cat convertToEntity(CatDto catDto) {
        return modelMapper.map(catDto, Cat.class);
    }

}


