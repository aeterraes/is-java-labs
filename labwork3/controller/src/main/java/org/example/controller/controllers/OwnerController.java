package org.example.controller.controllers;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.CatDto;
import org.example.controller.dto.OwnerDto;
import org.example.dataaccess.entity.Cat;
import org.example.dataaccess.entity.Owner;
import org.example.service.services.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    @Autowired
    private final OwnerService ownerService;
    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void saveOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = convertToEntity(ownerDto);
        ownerService.saveOwner(owner);
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable("id") int id) {
        Owner owner = ownerService.getOwnerById(id);
        return owner != null ? convertToDto(owner) : null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOwner(@PathVariable("id") int id) {
        ownerService.deleteOwner(id);
    }

    @PutMapping("/update/{id}")
    public OwnerDto updateOwner(@PathVariable("id") int id, @RequestBody OwnerDto ownerDto) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner != null) {
            owner.setName(ownerDto.getName());
            owner.setBirthDate(ownerDto.getBirthDate());
            ownerService.updateOwner(owner);
            return convertToDto(owner);
        } else {
            return null;
        }
    }

    @GetMapping("/{id}/cats")
    public List<CatDto> findAllCatsByOwnerId(@PathVariable int id) {
        return ownerService.findAllByOwnerId(id).stream().map(this::convertToCatDto).collect(Collectors.toList());
    }

    private CatDto convertToCatDto(Cat cat) {
        return modelMapper.map(cat, CatDto.class);
    }

    private OwnerDto convertToDto(Owner owner) {
        return modelMapper.map(owner, OwnerDto.class);
    }

    private Owner convertToEntity(OwnerDto ownerDto) {
        return modelMapper.map(ownerDto, Owner.class);
    }
}


