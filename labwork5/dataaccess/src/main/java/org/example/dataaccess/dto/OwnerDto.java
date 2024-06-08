package org.example.dataaccess.dto;

import lombok.Data;
import lombok.Value;
import org.example.dataaccess.entity.Cat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link org.example.dataaccess.entity.Owner}
 */
@Data
public class OwnerDto implements Serializable {
    int id;
    String name;
    LocalDate birthDate;
    List<Cat> cats;
}