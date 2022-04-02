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
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.respositories.BookRepository;
import reallylastone.librarymanagementsystem.services.AuthorService;
import reallylastone.librarymanagementsystem.services.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final Author correctAuthor = new Author("Jane", "Doe");

    private static final List<Author> incorrectAuthors = Arrays.asList(
            null,
            new Author(),
            new Author("", ""),
            new Author("John", ""),
            new Author("", "Doe")
    );

    private static final Book correctBook = Book.builder(1L, correctAuthor, "Example title").build();

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
                    () -> bookService.addBook(Book.builder(1L, author, "Title").build()));
        });
    }

    @Test
    public void shouldThrowInvalidRequestExceptionBecauseTitleIsInvalid() {
        Assertions.assertThrows(InvalidRequestException.class,
                () -> bookService.addBook(Book.builder(1L, correctAuthor, "").build()));
        Assertions.assertThrows(InvalidRequestException.class,
                () -> bookService.addBook(Book.builder(1L, correctAuthor, null).build()));
    }

    @Test
    public void shouldThrowEntityAlreadyExistsException() {
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);

        Assertions.assertThrows(EntityAlreadyExist.class,
                () -> bookService.addBook(Book.builder(1L, correctAuthor, "Title").build()));
    }

    @Test
    public void shouldThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    public void shouldReturnBook() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(correctBook));

        Assertions.assertNotEquals(bookService.getBookById(1L), null);
    }

    @Test
    public void shouldAddBook() {
        Mockito.when(authorService.isAuthorCorrect(correctAuthor)).thenReturn(true);
        bookService.addBook(correctBook);
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionBecauseIdNotPresent() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(correctBook.getISBN()));
    }

    @Test
    public void shouldDeleteBook() {
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);
        bookService.deleteBookById(1L);
    }
}