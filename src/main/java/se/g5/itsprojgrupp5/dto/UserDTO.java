package se.g5.itsprojgrupp5.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

/*
User data transfer objekt.
Validation, transfer.
Used to avoid accessing database directly.
 */
public class UserDTO {

    @NotEmpty
    @Email(message="Not a valid email adress")
    private String email;
    @NotEmpty
    @Size(min= 4, message="Password needs to be 4 letters or longer")
    private String password;
    @NotEmpty
    private String role;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Min(1)
    @Max(100)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
