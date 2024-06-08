package org.example.dataaccess.dto;

import lombok.Data;
import org.example.dataaccess.entity.Cat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link org.example.dataaccess.entity.Cat}
 */
@Data
public class CatDto implements Serializable {
    int id;
    String name;
    LocalDate birthDate;
    String breed;
    String color;
    int ownerId;
    List<Cat> friends;
    //OwnerDto owner;
}