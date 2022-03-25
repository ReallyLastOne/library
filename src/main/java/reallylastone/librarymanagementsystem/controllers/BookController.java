package reallylastone.librarymanagementsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.services.BookService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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
}
