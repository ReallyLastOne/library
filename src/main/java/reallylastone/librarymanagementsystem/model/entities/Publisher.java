package reallylastone.librarymanagementsystem.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Publisher {
    @Id
    private Long id;

    @OneToMany
    private List<Book> books;
}
