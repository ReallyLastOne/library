package reallylastone.librarymanagementsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;
import reallylastone.librarymanagementsystem.models.dto.BookDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return fName.equals(author.fName) && lName.equals(author.lName) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fName, lName, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", books=" + books.stream().map(BookDto::new).collect(Collectors.toList()) +
                '}';
    }
}
