package pt.isec.prompt;

import java.util.Optional;

public interface JsonDeserializer {
    <T> Optional<T> fromJson(String json, Class<T> type);
}
