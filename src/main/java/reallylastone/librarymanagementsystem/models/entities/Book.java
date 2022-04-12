package reallylastone.librarymanagementsystem.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Range;
import reallylastone.librarymanagementsystem.models.Dimension;
import reallylastone.librarymanagementsystem.models.PublicationPlace;
import reallylastone.librarymanagementsystem.utils.RangeSerializer;
import reallylastone.librarymanagementsystem.utils.RangeToString;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @Getter(AccessLevel.NONE)
    @JsonProperty("ISBN")
    private Long ISBN;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Author author;

    @Column(nullable = false)
    private String title;

    private String format;

    @Convert(converter = RangeToString.class)
    @JsonSerialize(using = RangeSerializer.class)
    private Range<Integer> ageRange = Range.unbounded();

    @Embedded
    private Dimension dimension;

    @ManyToOne
    private Publisher publisher;

    private Integer bestsellersRank;

    private String imprint;

    private LocalDateTime publicationDate;

    @Embedded
    private PublicationPlace publicationPlace;

    private Locale language;

    public static BookBuilder builder(Long ISBN, Author author, String title) {
        return new BookBuilder().ISBN(ISBN).author(author).title(title);
    }

    public Long getISBN() {
        return ISBN;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return author.equals(book.author) && title.equals(book.title) && Objects.equals(format, book.format) && Objects.equals(ageRange, book.ageRange) && Objects.equals(dimension, book.dimension) && Objects.equals(publisher, book.publisher) && Objects.equals(bestsellersRank, book.bestsellersRank) && Objects.equals(imprint, book.imprint) && Objects.equals(publicationDate, book.publicationDate) && Objects.equals(publicationPlace, book.publicationPlace) && Objects.equals(language, book.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, format, ageRange, dimension, publisher, bestsellersRank, imprint, publicationDate, publicationPlace, language);
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", author=" + author +
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
