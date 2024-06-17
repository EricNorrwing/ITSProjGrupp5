package se.g5.itsprojgrupp5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {


    @Autowired
    private MockMvc mvc;
    @Test
    @DisplayName("Testing that a registration will fail when incorrect input is sent")
    public void testFailedRegistration() throws Exception {
        mvc.perform(post("/register/user")
                        .with(httpBasic("admin@admin.se", "adminpass"))
                        .param("email", "InvalidEmailAdress")
                        .param("password", "p")
                        .param("role", "")
                        .param("name", "Sarah")
                        .param("surname", "Connor")
                        .param("age", "255"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userDTO", "email", "password", "role"));
    }
}
