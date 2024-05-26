package org.example.dataaccess.repository;

import org.example.dataaccess.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {

    List<Cat> findAllByColor(String color);
    List<Cat> findAllByBreed(String breed);
    List<Cat> findAllByOwnerId(int id);
    Cat findCatByName(String name);
}