package reallylastone.librarymanagementsystem.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reallylastone.librarymanagementsystem.models.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
