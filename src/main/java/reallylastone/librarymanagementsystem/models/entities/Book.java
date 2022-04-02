package reallylastone.librarymanagementsystem.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Range;
import reallylastone.librarymanagementsystem.models.Dimension;
import reallylastone.librarymanagementsystem.models.PublicationPlace;
import reallylastone.librarymanagementsystem.utils.RangeSerializer;
import reallylastone.librarymanagementsystem.utils.RangeToString;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Book {
    @Id
    @Getter(AccessLevel.NONE)
    @JsonProperty("ISBN")
    private Long ISBN;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Author author;

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
}
