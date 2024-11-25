
package pt.isec.prompt;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;

public class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
    // Serialize Duration to JSON
    @Override
    public JsonElement serialize(Duration duration, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(duration.toMinutes()); // Serialize as minutes
    }

    // Deserialize JSON to Duration
    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        long minutes = json.getAsLong(); // Get the long value (minutes)
        return Duration.ofMinutes(minutes); // Convert back to Duration
    }
}
