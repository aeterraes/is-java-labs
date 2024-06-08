package org.example.kotiki.service;

import lombok.RequiredArgsConstructor;
import org.example.dataaccess.dto.CatDto;
import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.repository.CatRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CatService {
@Autowired
    private final CatRepository catRepository;
@Autowired
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "saveCatQueue")
    public void saveCat(Cat cat) {
        catRepository.save(cat);
        rabbitTemplate.convertAndSend("catExchange", "saveCat", convertToDto(cat));
    }

    @RabbitListener(queues = "getCatByIdQueue")
    public Cat getCatById(int id) {
        return catRepository.findById(id).orElse(null);
    }

    @RabbitListener(queues = "getCatByNameQueue")
    public Cat getCatByName(String name) {
        return catRepository.findByName(name);
    }

    @RabbitListener(queues = "getAllCatsQueue")
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    @RabbitListener(queues = "updateCatQueue")
    public void updateCat(Cat cat) {
        catRepository.save(cat);
        rabbitTemplate.convertAndSend("catExchange", "updateCat", convertToDto(cat));
    }

    @RabbitListener(queues = "deleteCatQueue")
    public void deleteCat(int id) {
        catRepository.deleteById(id);
        rabbitTemplate.convertAndSend("catExchange", "deleteCat", id);
    }

    @RabbitListener(queues = "getCatsByColorQueue")
    public List<Cat> getCatsByColor(String color) {
        return catRepository.findAllByColor(color);
    }

    @RabbitListener(queues = "getCatsByBreedQueue")
    public List<Cat> getCatsByBreed(String breed) {
        return catRepository.findAllByBreed(breed);
    }

    private CatDto convertToDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setId(cat.getId());
        return catDto;
    }
}
