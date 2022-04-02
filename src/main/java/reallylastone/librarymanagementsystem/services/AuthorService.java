package reallylastone.librarymanagementsystem.services;

import org.apache.openjpa.lib.util.StringUtil;
import org.springframework.stereotype.Service;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.respositories.AuthorRepository;

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

    public Optional<Author> getAuthorByFNameAndLName(String fName, String lName) {
        return authorRepository.getByFNameAndLName(fName, lName);
    }

    public void delete(Author author) {
        authorRepository.deleteByFNameAndLName(author.getFName(), author.getLName());
    }
}
