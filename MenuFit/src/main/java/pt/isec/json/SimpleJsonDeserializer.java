package pt.isec.json;

import java.util.Optional;

public interface SimpleJsonDeserializer {
    <T> Optional<T> fromJson(String json, Class<T> type);
}
