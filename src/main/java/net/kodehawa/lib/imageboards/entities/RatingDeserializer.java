package net.kodehawa.lib.imageboards.entities;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.exc.InvalidFormatException;

public class RatingDeserializer extends StdDeserializer<Rating> {

    public RatingDeserializer() {
        super(Rating.class);
    }

    @Override
    public Rating deserialize(JsonParser parser, DeserializationContext ctx)
           throws JacksonException {
        String value = parser.getValueAsString();
        Rating rating = Rating.lookupFromStringShort(value);
        if (rating != null) {
            return rating;
        }
        rating = Rating.lookupFromString(value);
        if (rating != null) {
            return rating;
        }
        throw InvalidFormatException.from(
                parser,
                "Unable to deserialize rating: " + value,
                value,
                Rating.class
        );
    }

}
