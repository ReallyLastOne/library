package reallylastone.librarymanagementsystem.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Range;

import java.io.IOException;

public class RangeSerializer extends JsonSerializer<Range<Integer>> {

    @Override
    public void serialize(Range range, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(range.toString());
    }
}
