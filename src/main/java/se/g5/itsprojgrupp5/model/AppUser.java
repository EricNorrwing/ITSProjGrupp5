package se.g5.itsprojgrupp5.model;

import jakarta.persistence.*;

// TODO Add class comment
@Entity
@Table(name = "users")
public class AppUser {

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
