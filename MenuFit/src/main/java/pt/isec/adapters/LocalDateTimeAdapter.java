package pt.isec.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    // Define the date-time format to match the JSON input (ISO-8601 in this case)
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Serialize LocalDateTime to JSON
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context) {
        String date = localDateTime.toString();
        return context.serialize(date); // Serialize as minutes
    }

    // Deserialize JSON to LocalDateTime
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString(); // Get the long value (minutes)
        return LocalDateTime.parse(date, formatter); // Convert back to Duration
    }
}
