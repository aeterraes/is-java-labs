package org.example.dataaccess.dto;

import lombok.Data;
import org.example.dataaccess.entity.Owner;
import org.example.dataaccess.model.Role;

import java.io.Serializable;

/**
 * DTO for {@link org.example.dataaccess.entity.User}
 */
@Data
public class UserDto implements Serializable {
    int userId;
    String username;
    String password;
    Role role;
    Owner owner;
}