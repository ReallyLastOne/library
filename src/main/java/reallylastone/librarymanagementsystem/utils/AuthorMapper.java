package reallylastone.librarymanagementsystem.utils;

import org.springframework.stereotype.Component;
import reallylastone.librarymanagementsystem.models.dto.AuthorDto;
import reallylastone.librarymanagementsystem.models.dto.AuthorView;
import reallylastone.librarymanagementsystem.models.entities.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public AuthorDto entityToDto(Author author) {
        return new AuthorDto(
                author.getId(), author.getFName(), author.getLName()
        );
    }

    public AuthorView entityToView(Author author) {
        return new AuthorView(author);
    }

    public List<AuthorView> entitiesToView(List<Author> authors) {
        return authors.stream().map(AuthorView::new).collect(Collectors.toList());
    }

    public Author dtoToEntity(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getFName(), authorDto.getFName(), new ArrayList<>());
    }

    public Author viewToEntity(AuthorView authorView) {
        return new Author(authorView.getId(), authorView.getFName(), authorView.getLName(), new ArrayList<>());
    }
}
