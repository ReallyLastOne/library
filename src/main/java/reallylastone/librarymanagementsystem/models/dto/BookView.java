package reallylastone.librarymanagementsystem.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.domain.Range;
import reallylastone.librarymanagementsystem.models.Dimension;
import reallylastone.librarymanagementsystem.models.PublicationPlace;
import reallylastone.librarymanagementsystem.models.entities.Book;
import reallylastone.librarymanagementsystem.models.entities.Publisher;
import reallylastone.librarymanagementsystem.utils.RangeSerializer;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/** Used to display in API. The difference is lack of Books in Author entity. */
public class BookView {
    @JsonProperty("ISBN")
    @Getter(AccessLevel.NONE)
    private Long ISBN;
    private String title;
    private String format;
    @JsonSerialize(using = RangeSerializer.class)
    private Range<Integer> ageRange = Range.unbounded();
    private Dimension dimension;
    private Publisher publisher;
    private Integer bestsellersRank;
    private String imprint;
    private LocalDateTime publicationDate;
    private PublicationPlace publicationPlace;
    private Locale language;
    private AuthorDto author;

    public BookView(Book book) {
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
        this.author = new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFName(), book.getAuthor().getLName());
    }

    @Override
    public String toString() {
        return "BookView{" +
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
                ", author=" + author +
                '}';
    }

    public Long getISBN() {
        return ISBN;
    }
}
