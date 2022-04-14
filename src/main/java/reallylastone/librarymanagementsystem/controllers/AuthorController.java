package reallylastone.librarymanagementsystem.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reallylastone.librarymanagementsystem.models.dto.AuthorView;
import reallylastone.librarymanagementsystem.models.dto.BookView;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.services.AuthorService;
import reallylastone.librarymanagementsystem.utils.AuthorMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping
    public List<AuthorView> getAllAuthors() {
        return authorMapper.entitiesToView(authorService.getAll());
    }

    @GetMapping("/{id}")
    public AuthorView getById(@PathVariable(name = "id") Long id) {
        return authorMapper.entityToView(authorService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<AuthorView> addAuthor(@RequestBody AuthorView author) {
        Author dbAuthor = authorService.save(authorMapper.viewToEntity(author));

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/api/v1/authors/{id}").build()
                .expand(dbAuthor.getId()).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(authorMapper.entityToView(dbAuthor), headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorView> deleteAuthor(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<AuthorView> updateAuthor(@RequestBody AuthorView author, @PathVariable(name = "id") Long id) {
        authorService.updateAuthor(authorMapper.viewToEntity(author), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public ResponseEntity<AuthorView> patchAuthor(@PathVariable(name = "id") Long id, @RequestBody AuthorView authorView) {
        Author toUpdate = authorService.getById(id);
        authorService.patchAuthor(toUpdate, authorMapper.viewToEntity(authorView));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
