package reallylastone.librarymanagementsystem.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reallylastone.librarymanagementsystem.models.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
