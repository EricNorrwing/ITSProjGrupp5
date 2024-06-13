package se.g5.itsprojgrupp5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.g5.itsprojgrupp5.model.AppUser;

//TODO Class description
/**
 * Info om HakansClass här, bla bla bla, så här funkar den klassen osv.
 * Mer info om denna klass..
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

}
