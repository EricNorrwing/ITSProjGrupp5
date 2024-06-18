package se.g5.itsprojgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.g5.itsprojgrupp5.model.AppUser;


/*
Class to fetch data from database.
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    boolean existsByEmail(String email);
}
