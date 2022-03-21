package reallylastone.librarymanagementsystem.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents dimensions of a book.
 */
@Embeddable
@Getter
@Setter
public class Dimension {
    private Integer width; // in mm
    private Integer height; // in mm
    private Integer depth; // in mm
    private Integer weight; // in grams
}
