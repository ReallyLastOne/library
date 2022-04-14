package reallylastone.librarymanagementsystem.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reallylastone.librarymanagementsystem.models.entities.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Used to display in API. The difference is lack of Author in Book entity.
 */
public class AuthorView {
    private Long id;
    @JsonProperty("fName")
    private String fName;
    @JsonProperty("lName")
    private String lName;
    private List<BookDto> books = new ArrayList<>();

    public AuthorView(Author author) {
        this.id = author.getId();
        this.fName = author.getFName();
        this.lName = author.getLName();
        this.books = author.getBooks().stream().map(BookDto::new).collect(Collectors.toList());
    }
}
