package reallylastone.librarymanagementsystem.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reallylastone.librarymanagementsystem.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
