package reallylastone.librarymanagementsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Range;
import org.springframework.lang.Nullable;
import reallylastone.librarymanagementsystem.models.Dimension;
import reallylastone.librarymanagementsystem.models.PublicationPlace;
import reallylastone.librarymanagementsystem.utils.RangeToString;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@ToString
public class Book {
    @Id
    @NonNull
    private Long ISBN;

    @ManyToOne
    @NonNull
    private Author author;

    @NonNull
    private String title;

    private String format;

    @Convert(converter = RangeToString.class)
    private Range<Integer> ageRange;

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
}
