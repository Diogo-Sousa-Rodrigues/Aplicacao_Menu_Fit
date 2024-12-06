package pt.isec.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    // Define the date format to match the JSON input (ISO-8601 in this case)
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // Serialize LocalDateTime to JSON
    @Override
    public JsonElement serialize(LocalDate localDate, Type typeOfSrc, JsonSerializationContext context) {
        String date = localDate.toString();
        return context.serialize(date); // Serialize as minutes
    }

    // Deserialize JSON to LocalDateTime
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString(); // Get the long value (minutes)
        return LocalDate.parse(date, formatter); // Convert back to Duration
    }
}
