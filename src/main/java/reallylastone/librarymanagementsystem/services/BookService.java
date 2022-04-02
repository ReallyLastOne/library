package reallylastone.librarymanagementsystem.services;

import org.apache.openjpa.lib.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reallylastone.librarymanagementsystem.exceptions.EntityAlreadyExist;
import reallylastone.librarymanagementsystem.exceptions.EntityNotFoundException;
import reallylastone.librarymanagementsystem.exceptions.InvalidRequestException;
import reallylastone.librarymanagementsystem.models.entities.Author;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.respositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book with ISBN = " + id + " not found."));
    }

    @Transactional
    public Book addBook(Book book) {
        if (book.getISBN() == null) {
            throw new InvalidRequestException("ISBN must not be a null.");
        } else if (!bookRepository.existsById(book.getISBN())) {
            if (!authorService.isAuthorCorrect(book.getAuthor())) {
                throw new InvalidRequestException("An author must not be null and must have a name and last name.");
            } else if (StringUtil.isBlank(book.getTitle())) {
                throw new InvalidRequestException("A book must have a title.");
            } else {
                // set Author from database if already exist
                Author author = book.getAuthor();
                Optional<Author> dbAuthor = authorService.getAuthorByFNameAndLName(author.getFName(), author.getLName());
                dbAuthor.ifPresent(book::setAuthor);
                return bookRepository.save(book);
            }
        } else {
            throw new EntityAlreadyExist("Book with ISBN = " + book.getISBN() + " already exist.");
        }
    }

    @Transactional
    public void deleteBookById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("ISBN must not be a null.");
        } else if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Book with ISBN = " + id + " not found.");
        }
    }

    @Transactional
    public void updateBook(Long id, Book book) {
        Book dbBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ISBN = " + id + " not found."));
        bookRepository.delete(dbBook);
        addBook(book); // bad practice?
    }
}
