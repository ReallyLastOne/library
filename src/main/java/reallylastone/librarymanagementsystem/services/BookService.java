package reallylastone.librarymanagementsystem.services;

import org.apache.openjpa.lib.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reallylastone.librarymanagementsystem.exceptions.EntityAlreadyExist;
import reallylastone.librarymanagementsystem.exceptions.EntityNotFoundException;
import reallylastone.librarymanagementsystem.exceptions.InvalidRequestException;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.respositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public Book getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book with ISBN = " + id + " not found.");
        }
        return bookOptional.get();
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(Book book) {
        if (book.getISBN() == null) {
            throw new InvalidRequestException("ISBN must not be null.");
        } else if (!bookRepository.existsById(book.getISBN())) {
            if (!authorService.isAuthorCorrect(book.getAuthor())) {
                throw new InvalidRequestException("An author must not be null and must have a name and last name.");
            } else if (StringUtil.isBlank(book.getTitle())) {
                throw new InvalidRequestException("A book must have a title.");
            } else {
                return bookRepository.save(book);
            }
        } else {
            throw new EntityAlreadyExist("Book with ISBN = " + book.getISBN() + " already exist.");
        }
    }
}
