package reallylastone.librarymanagementsystem.unit.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reallylastone.librarymanagementsystem.exceptions.EntityAlreadyExist;
import reallylastone.librarymanagementsystem.exceptions.EntityNotFoundException;
import reallylastone.librarymanagementsystem.exceptions.InvalidRequestException;
import reallylastone.librarymanagementsystem.exceptions.RequestNotAllowedException;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.respositories.AuthorRepository;
import reallylastone.librarymanagementsystem.services.AuthorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private static final List<Author> incorrectAuthors = Arrays.asList(
            null,
            new Author(),
            new Author("", ""),
            new Author("John", ""),
            new Author("", "Doe")
    );

    private final Author correctAuthor = new Author("John", "Doe");
    private final Book correctBook = Book.builder(1L, correctAuthor, "Example title").build();

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void shouldThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorService.getById(1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthorById(1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorService.updateAuthor(correctAuthor,1L));
    }

    @Test
    public void shouldThrowInvalidRequestException() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(correctAuthor));

        incorrectAuthors.forEach(author -> Assertions.assertThrows(InvalidRequestException.class, () -> authorService.save(author)));
        incorrectAuthors.forEach(author -> Assertions.assertThrows(InvalidRequestException.class, () -> authorService.updateAuthor(author, 1L)));
    }

    @Test
    public void shouldThrowEntityAlreadyExistException() {
        Mockito.when(authorRepository.getByFNameAndLName(correctAuthor.getFName(), correctAuthor.getLName()))
                .thenReturn(Optional.of(correctAuthor));

        Assertions.assertThrows(EntityAlreadyExist.class, () -> authorService.save(correctAuthor));
    }

    @Test
    public void shouldThrowRequestNotAllowedException() {
        correctAuthor.addBook(correctBook);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(correctAuthor));

        Assertions.assertThrows(RequestNotAllowedException.class, () -> authorService.deleteAuthorById(1L));
    }

    @Test
    public void shouldAddAuthor() {
        authorService.save(correctAuthor);
    }

    @Test
    public void shouldGetAuthors() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(correctAuthor));
        authorService.getById(1L);
        authorService.getAll();
    }

    @Test
    public void shouldUpdateAuthor() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(correctAuthor));
        authorService.updateAuthor(new Author("Georg", "Cantor"), 1L);
    }

    @Test
    public void shouldDeleteAuthor() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(correctAuthor));
        authorService.deleteAuthorById(1L);
    }
}

