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
        this.ageRange = book.getAgeRange();
        this.bestsellersRank = book.getBestsellersRank();
        this.dimension = book.getDimension();
        this.format = book.getFormat();
        this.imprint = book.getFormat();
        this.ISBN = book.getISBN();
        this.language = book.getLanguage();
        this.publicationPlace = book.getPublicationPlace();
        this.publicationDate = book.getPublicationDate();
        this.title = book.getTitle();
        this.publisher = book.getPublisher();
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "ISBN=" + ISBN +
                ", title='" + title + '\'' +
                ", format='" + format + '\'' +
                ", ageRange=" + ageRange +
                ", dimension=" + dimension +
                ", publisher=" + publisher +
                ", bestsellersRank=" + bestsellersRank +
                ", imprint='" + imprint + '\'' +
                ", publicationDate=" + publicationDate +
                ", publicationPlace=" + publicationPlace +
                ", language=" + language +
                '}';
    }
}
