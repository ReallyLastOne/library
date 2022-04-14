package reallylastone.librarymanagementsystem.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reallylastone.librarymanagementsystem.models.dto.BookView;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.services.AuthorService;
import reallylastone.librarymanagementsystem.services.BookService;
import reallylastone.librarymanagementsystem.utils.BookMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, AuthorService authorService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    @GetMapping(value = {"", "/"})
    public List<BookView> getAllBooks() {
        return bookMapper.entitiesToView(bookService.getAll());
    }

    @GetMapping("/{id}")
    public BookView getBookById(@PathVariable(name = "id") Long id) {
        return bookMapper.entityToView(bookService.getBookById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<BookView> addBook(@RequestBody BookView bookView) {
        Book dbBook = bookService.addBook(bookMapper.viewToEntity(bookView));

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/v1/books/{id}").build()
                .expand(dbBook.getISBN()).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(bookMapper.entityToView(dbBook), headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookView> deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<BookView> updateBook(@PathVariable(name = "id") Long id, @RequestBody BookView book) {
        bookService.updateBook(id, bookMapper.viewToEntity(book));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<BookView> patchBook(@PathVariable(name = "id") Long id, @RequestBody BookView book) {
        Book toUpdate = bookService.getBookById(id);
        bookService.patchBook(toUpdate, bookMapper.viewToEntity(book));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}