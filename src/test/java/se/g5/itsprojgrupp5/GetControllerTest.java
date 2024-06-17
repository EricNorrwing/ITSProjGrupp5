package se.g5.itsprojgrupp5;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("testing that / cannot be access unless authorized")
    public void assertThatUserHasToBeLoggedIn() throws Exception {
        mvc.perform(get("/").with(httpBasic("user", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Asserting that get /admin can be accessed by ADMIN user")
    public void assertThatAdminUserCanAccessAdminPage() throws Exception {
        mvc.perform(get("/admin/page").with(httpBasic("admin@admin.se", "adminpass")))
                .andExpect(status().isOk());
    }

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
