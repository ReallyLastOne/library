package reallylastone.librarymanagementsystem.model.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reallylastone.librarymanagementsystem.model.PublicationPlace;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    private Long ISBN;

    @ManyToOne
    private Author author;

    private String title;

    @ManyToOne
    private Publisher publisher;

    private LocalDateTime publicationDate;

    @Embedded
    private PublicationPlace publicationPlace;
    private Locale language;
}
