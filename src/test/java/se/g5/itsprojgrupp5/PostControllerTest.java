package se.g5.itsprojgrupp5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {


    @Autowired
    private MockMvc mvc;
    @Test
    @DisplayName("Test user registration with invalid data")
    public void testUserRegistrationValidationErrors() throws Exception {
        mvc.perform(get("/register/user")
                .with(httpBasic("admin@admin.se", "adminpass")))
                .andExpect(status().isOk())
                .andExpect(view().name("registerUser"));


        mvc.perform(post("/register/user")
                        .with(httpBasic("admin@admin.se", "adminpass"))
                        .param("email", "invalidEmail")
                        .param("password", "p")
                        .param("name", "")
                        .param("surname", "Doe")
                        .param("age", "17"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerUser"));
    }


    @Test
    @DisplayName("Test registration with correct input")
    public void testRegistrationWithCorrectInput() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("valid@valid.com");
        userDTO.setPassword("password");
        userDTO.setRole("USER");
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setAge(30);

        mvc.perform(post("/register/user")
                        .with(httpBasic("admin@admin.se", "adminpass"))
                        .flashAttr("userDTO", userDTO))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing that you cannot remove a user that does not exist")
    public void testRemovalOfUser() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("user@user.se");

        mvc.perform(post("/remove/user")
                        .with(httpBasic("admin@admin.se", "adminpass"))
                        .flashAttr("email", emailDTO))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing that you cannot remove a user that does not exist")
    public void testFailedRemovalOfUser() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("esfsefe");

        mvc.perform(post("/remove/user")
                        .with(httpBasic("admin@admin.se", "adminpass"))
                        .flashAttr("email", emailDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("search"));
    }
}
