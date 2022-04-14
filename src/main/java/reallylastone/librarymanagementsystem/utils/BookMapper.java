package reallylastone.librarymanagementsystem.utils;

import org.springframework.stereotype.Component;
import reallylastone.librarymanagementsystem.models.dto.BookView;
import reallylastone.librarymanagementsystem.models.entities.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookView entityToView(Book book) {
        return new BookView(book);
    }

    public List<BookView> entitiesToView(List<Book> books) {
        return books.stream().map(this::entityToView).collect(Collectors.toList());
    }

    public Book viewToEntity(BookView bookView) {
        return Book.builder(bookView.getISBN(), authorMapper.dtoToEntity(bookView.getAuthor()), bookView.getTitle())
                .ageRange(bookView.getAgeRange()).bestsellersRank(bookView.getBestsellersRank())
                .dimension(bookView.getDimension()).format(bookView.getFormat()).imprint(bookView.getImprint())
                .language(bookView.getLanguage()).publicationPlace(bookView.getPublicationPlace())
                .publicationDate(bookView.getPublicationDate()).publisher(bookView.getPublisher()).build();
    }
}
