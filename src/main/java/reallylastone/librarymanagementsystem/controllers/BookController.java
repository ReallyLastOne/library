package reallylastone.librarymanagementsystem.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.services.AuthorService;
import reallylastone.librarymanagementsystem.services.BookService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable(name = "id") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book dbBook = bookService.addBook(book);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/v1/books/{id}").build()
                .expand(dbBook.getISBN()).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(dbBook, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        bookService.updateBook(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}