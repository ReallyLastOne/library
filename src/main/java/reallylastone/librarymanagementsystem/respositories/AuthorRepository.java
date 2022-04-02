package reallylastone.librarymanagementsystem.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reallylastone.librarymanagementsystem.models.entities.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a where a.fName = :fName and a.lName = :lName")
    Optional<Author> getByFNameAndLName(String fName, String lName);

    @Modifying
    @Query("delete from Author a where a.fName = :fName and a.lName = :lName")
    void deleteByFNameAndLName(String fName, String lName);
}
