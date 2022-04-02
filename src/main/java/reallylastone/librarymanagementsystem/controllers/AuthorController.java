package reallylastone.librarymanagementsystem.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.services.AuthorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable(name = "id") Long id) {
        return authorService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author dbAuthor = authorService.save(author);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/v1/authors/{id}").build()
                .expand(dbAuthor.getId()).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(dbAuthor, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deteleAuthor(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable(name = "id") Long id) {
        authorService.updateAuthor(author, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
