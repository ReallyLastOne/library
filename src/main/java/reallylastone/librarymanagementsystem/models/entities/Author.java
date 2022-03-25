package reallylastone.librarymanagementsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fName;

    @NonNull
    private String lName;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
