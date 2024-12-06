package pt.isec.json;

import java.lang.reflect.Type;
import java.util.Optional;

public interface SimpleJsonDeserializer {
    <T> Optional<T> fromJson(String json, Type type);
}
