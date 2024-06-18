package se.g5.itsprojgrupp5.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
User of type UserDetails.
AppUser is Default User for spring security.
Replaces spring security cure user.
 */

@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int age;

    public AppUser() {
    }

    private AppUser(AppUserBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.name = builder.name;
        this.surname = builder.surname;
        this.age = builder.age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class AppUserBuilder {
        private Long id;
        private String email;
        private String password;
        private String role;
        private String name;
        private String surname;
        private int age;

        public AppUserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AppUserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AppUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AppUserBuilder withRole(String role) {
            this.role = role;
            return this;
        }

        public AppUserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AppUserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public AppUserBuilder withAge(int age) {
            this.age = age;
            return this;
        }

        public AppUser build() {
            return new AppUser(this);
        }
    }
}
