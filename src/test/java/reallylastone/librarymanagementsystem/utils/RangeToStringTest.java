package reallylastone.librarymanagementsystem.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Range;

import java.util.Map;

import static java.util.Map.entry;

public class RangeToStringTest {

    private static final Map<Range<Integer>, String> correctPairs = Map.ofEntries(
            entry(Range.closed(0, 1), "[0-1]"),
            entry(Range.open(0, 1), "(0-1)"),
            entry(Range.rightUnbounded(Range.Bound.exclusive(0)), "(0-unbounded"),
            entry(Range.rightUnbounded(Range.Bound.inclusive(0)), "[0-unbounded"),
            entry(Range.leftUnbounded(Range.Bound.exclusive(0)), "unbounded-0)"),
            entry(Range.leftUnbounded(Range.Bound.inclusive(0)), "unbounded-0]"),
            entry(Range.unbounded(), "unbounded-unbounded"),
            entry(Range.rightOpen(0, 1), "[0-1)"),
            entry(Range.leftOpen(0, 1), "(0-1]")
    );

    private RangeToString rangeToString;

    @BeforeEach
    public void setUp() {
        rangeToString = new RangeToString();
    }

    @Test
    public void shouldReturnCorrectRange() {
        correctPairs.forEach((k, v) -> Assertions.assertEquals(rangeToString.convertToEntityAttribute(v), k));
    }

    @Test
    public void shouldReturnCorrectString() {
        correctPairs.forEach((k, v) -> Assertions.assertEquals(rangeToString.convertToDatabaseColumn(k), v));
    }
}
