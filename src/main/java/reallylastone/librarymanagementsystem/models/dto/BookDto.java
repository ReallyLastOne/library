package reallylastone.librarymanagementsystem.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Range;
import reallylastone.librarymanagementsystem.models.Dimension;
import reallylastone.librarymanagementsystem.models.PublicationPlace;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.models.entities.Publisher;

import java.time.LocalDateTime;
import java.util.Locale;
@Getter
@Setter
public class BookDto {
    private Long ISBN;
    private String title;
    private String format;
    private Range<Integer> ageRange = Range.unbounded();
    private Dimension dimension;
    private Publisher publisher;
    private Integer bestsellersRank;
    private String imprint;
    private LocalDateTime publicationDate;
    private PublicationPlace publicationPlace;
    private Locale language;

    public BookDto(Book book) {
        this.ISBN = book.getISBN();
        this.title = book.getTitle();
        this.format = book.getFormat();
        this.ageRange = book.getAgeRange();
        this.dimension = book.getDimension();
        this.publisher = book.getPublisher();
        this.bestsellersRank = book.getBestsellersRank();
        this.imprint = book.getImprint();
        this.publicationDate = book.getPublicationDate();
        this.publicationPlace = book.getPublicationPlace();
    }
}
