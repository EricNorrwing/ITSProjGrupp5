package se.g5.itsprojgrupp5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
UpdateUser DTO.
Validate, transfer.
 */

public class UpdateUserDTO {

    @NotEmpty
    @Email(message="Not a valid email adress")
    private String email;
    @NotEmpty
    @Size(min= 4, message="Password needs to be 4 letters or longer")
    private String password;

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
