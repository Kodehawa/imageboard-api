package net.kodehawa.lib.imageboards.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RatingDeserializer extends JsonDeserializer<Rating> {

    @Override
    public Rating deserialize(JsonParser parser, DeserializationContext ctx)
            throws IOException {
        String value = parser.getValueAsString();
        Rating rating = Rating.lookupFromStringShort(value);
        if (rating != null) {
            return rating;
        }
        rating = Rating.lookupFromString(value);
        if (rating != null) {
            return rating;
        }
        throw new IOException("Unable to deserialize rating " + value);
    }

}
