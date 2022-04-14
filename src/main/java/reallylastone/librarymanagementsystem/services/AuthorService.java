package reallylastone.librarymanagementsystem.services;

import org.apache.openjpa.lib.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reallylastone.librarymanagementsystem.exceptions.EntityAlreadyExist;
import reallylastone.librarymanagementsystem.exceptions.EntityNotFoundException;
import reallylastone.librarymanagementsystem.exceptions.InvalidRequestException;
import reallylastone.librarymanagementsystem.exceptions.RequestNotAllowedException;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.respositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository bookRepository) {
        this.authorRepository = bookRepository;
    }

    public boolean isAuthorCorrect(Author author) {
        return author != null
                && !StringUtil.isBlank(author.getFName())
                && !StringUtil.isBlank(author.getLName());
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Author with ID = " + id + " not found."));
    }

    public Optional<Author> getAuthorByFNameAndLName(String fName, String lName) {
        return authorRepository.getByFNameAndLName(fName, lName);
    }

    @Transactional
    public Author save(Author author) {
        if (isAuthorCorrect(author)) {
            if (authorRepository.getByFNameAndLName(author.getFName(), author.getLName()).isPresent()) {
                throw new EntityAlreadyExist("Author with fName = " + author.getFName() + " and lName = "
                        + author.getLName() + " already exists.");
            }
            return authorRepository.save(author);
        } else {
            throw new InvalidRequestException("Author must not be null nor any name must be null.");
        }
    }

    @Transactional
    public void updateAuthor(Author author, Long id) {
        Author dbAuthor = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with ID = " + id + " not found."));
        if (isAuthorCorrect(author)) {
            dbAuthor.setFName(author.getFName());
            dbAuthor.setLName(author.getLName());
            authorRepository.save(dbAuthor);
        } else {
            throw new InvalidRequestException("Author must not be null nor any name must be null.");
        }
    }

    @Transactional
    public void deleteAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with ID = " + id + " not found."));
        if (author.getBooks().isEmpty()) authorRepository.deleteById(id);
        else {
            throw new RequestNotAllowedException("Author with books can not be deleted.");
        }
    }

    @Transactional
    public void patchAuthor(Author toUpdate, Author author) {
        toUpdate.setFName(author.getFName());
        toUpdate.setLName(author.getLName());
        authorRepository.save(toUpdate);
    }
}
