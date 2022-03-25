package reallylastone.librarymanagementsystem.utils;

import jakarta.persistence.AttributeConverter;
import org.springframework.data.domain.Range;

public class RangeToString implements AttributeConverter<Range<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(Range<Integer> range) {
        return range.toString();
    }

    @Override
    public Range<Integer> convertToEntityAttribute(String s) {
        // ugly... but it works
        String left = s.substring(0, 1);
        String right = s.substring(s.length() - 1);
        Range<Integer> range;
        String[] el = s.split("-");
        if ("[".equals(left) && "]".equals(right)) { // [a-b]
            return Range.closed(Integer.parseInt(s.substring(1, 2)), Integer.parseInt(s.substring(s.length() - 2, s.length() - 1)));
        } else if ("(".equals(left) && ")".equals(right)) { // (a-b)
            return Range.open(Integer.parseInt(s.substring(1, 2)), Integer.parseInt(s.substring(s.length() - 2, s.length() - 1)));
        } else if ("(".equals(left) && "]".equals(right)) { // (a-b]
            return Range.leftOpen(Integer.parseInt(el[0].substring(1)), Integer.parseInt(el[1].substring(0, el[1].length() - 1)));
        } else if ("[".equals(left) && ")".equals(right)) { // [a-b)
            return Range.rightOpen(Integer.parseInt(el[0].substring(1)), Integer.parseInt(el[1].substring(0, el[1].length() - 1)));
        } else if ("[".equals(left)) { // [a-unbounded
            return Range.rightUnbounded(Range.Bound.inclusive(Integer.parseInt(el[0].substring(1))));
        } else if ("(".equals(left)) { // (a-unbounded
            return Range.rightUnbounded(Range.Bound.exclusive(Integer.parseInt(el[0].substring(1))));
        } else if ("]".equals(right)) { // unbounded-a]
            return Range.leftUnbounded(Range.Bound.inclusive(Integer.parseInt(el[1].substring(0, el[1].length() - 1))));
        } else if (")".equals(right)) { // unbounded-a)
            return Range.leftUnbounded(Range.Bound.exclusive(Integer.parseInt(el[1].substring(0, el[1].length() - 1))));
        }

        // unbounded - unbounded
        return Range.unbounded();
    }
}
