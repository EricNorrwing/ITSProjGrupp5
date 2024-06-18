package se.g5.itsprojgrupp5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/*
Email DTO class.
Validation, transfer.
 */

public class EmailDTO {

    @NotEmpty
    @Email(message="Not a valid email adress")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
