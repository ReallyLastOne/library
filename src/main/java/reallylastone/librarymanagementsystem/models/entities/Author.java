package reallylastone.librarymanagementsystem.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"fName", "lName"}))
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fName;

    @NonNull
    private String lName;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return fName.equals(author.fName) && lName.equals(author.lName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fName, lName);
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
}
