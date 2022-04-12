package reallylastone.librarymanagementsystem.dto;

import lombok.Getter;
import reallylastone.librarymanagementsystem.models.entities.Book;

import java.util.List;

@Getter
public class AuthorDto {
    private String fName;

    private String lName;

    private List<Book> books;
}
