package org.example.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dataaccess.dto.CatDto;
import org.example.dataaccess.entity.Cat;
import org.example.kotiki.service.CatService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;


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
        rabbitTemplate.convertAndSend("saveCat", catDto);
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable int id) {
        Cat cat = catService.getCatById(id);
        return cat != null ? convertToDto(cat) : null;
    }

    @GetMapping("/name/{name}")
    public CatDto getCatByName(@PathVariable("name") String name) {
        Cat cat = catService.getCatByName(name);
        return cat != null ? convertToDto(cat) : null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCat(@PathVariable("id") int id) {
        catService.deleteCat(id);
        rabbitTemplate.convertAndSend("deleteCat", id);
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
            rabbitTemplate.convertAndSend("updateCat", catDto);
            return convertToDto(cat);
        } else {
            return null;
        }
    }

    @GetMapping("/color/{color}")
    public List<CatDto> getCatsByColor(@PathVariable("color") String color) {
        List<CatDto> catDtos = catService.getCatsByColor(color).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        for (CatDto catDto : catDtos) {
            rabbitTemplate.convertAndSend("catExchange", "findByColorCat", catDto);
        }
        return catDtos;
    }

    @GetMapping("/breed/{breed}")
    public List<CatDto> getCatsByBreed(@PathVariable("breed") String breed) {
        List<CatDto> catDtos = catService.getCatsByBreed(breed).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        for (CatDto catDto : catDtos) {
            rabbitTemplate.convertAndSend("catExchange", "findByBreedCat", catDto);
        }
        return catDtos;
    }

    private CatDto convertToDto(Cat cat) {
        return modelMapper.map(cat, CatDto.class);
    }

    private Cat convertToEntity(CatDto catDto) {
        return modelMapper.map(catDto, Cat.class);
    }
}
