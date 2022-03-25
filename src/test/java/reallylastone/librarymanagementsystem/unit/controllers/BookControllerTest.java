package reallylastone.librarymanagementsystem.unit.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reallylastone.librarymanagementsystem.exceptions.EntityAlreadyExist;
import reallylastone.librarymanagementsystem.exceptions.InvalidRequestException;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.respositories.BookRepository;
import reallylastone.librarymanagementsystem.services.AuthorService;
import reallylastone.librarymanagementsystem.services.BookService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    private static final Author correctAuthor = new Author("Jane", "Doe");

    private static final List<Author> incorrectAuthors = Arrays.asList(
            null,
            new Author(),
            new Author("", ""),
            new Author("John", ""),
            new Author("", "Doe")
    );

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Test
    public void shouldThrowInvalidRequestExceptionBecauseIsbnIsInvalid() {
        Assertions.assertThrows(InvalidRequestException.class, () ->
                bookService.addBook(Book.builder(null, correctAuthor, "Title").build()));

        Assertions.assertThrows(InvalidRequestException.class, () ->
                bookService.addBook(new Book()));
    }

    @Test
    public void shouldThrowInvalidRequestExceptionBecauseAuthorIsInvalid() {
        incorrectAuthors.forEach(author -> {
            Assertions.assertThrows(InvalidRequestException.class,
                    () -> bookService.addBook(Book.builder(125L, author, "Title").build()));
        });
    }

    @Test
    public void shouldThrowInvalidRequestExceptionBecauseTitleIsInvalid() {
        Assertions.assertThrows(InvalidRequestException.class,
                () -> bookService.addBook(Book.builder(125L, correctAuthor, "").build()));
        Assertions.assertThrows(InvalidRequestException.class,
                () -> bookService.addBook(Book.builder(125L, correctAuthor, null).build()));
    }

    @Test
    public void shouldThrowEntityAlreadyExistsException() {
        Mockito.when(bookRepository.existsById(125L)).thenReturn(true);

        Assertions.assertThrows(EntityAlreadyExist.class,
                () -> bookService.addBook(Book.builder(125L, correctAuthor, "Title").build()));
    }
}