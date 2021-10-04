package cn.wolves.config.jsonConfig;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * @author Jumping
 */
public class LongToStringSerializer extends JsonSerializer<Long> {

    public static final long MAX_LONG_TO_STRING = (long) Math.pow(10, 15);

    public static final LongToStringSerializer INSTANCE = new LongToStringSerializer();

    @Override
    public void serialize(Long aLong,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        if (aLong != null) {
            if (aLong > MAX_LONG_TO_STRING) {
                jsonGenerator.writeString(aLong.toString());
            } else {
                jsonGenerator.writeNumber(aLong);
            }
        }
    }
}