package pt.isec.prompt;

import java.util.Optional;

public interface SimpleJsonDeserializer {
    <T> Optional<T> fromJson(String json, Class<T> type);
}
