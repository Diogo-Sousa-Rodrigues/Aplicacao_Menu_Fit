package pt.isec.prompt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pt.isec.ai.CommonLLM;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

//public class InstanceBuilder implements JsonDeserializer {
//    public <T> Optional<T> getInstance(String prompt, CommonLLM llm,
//                                              Class<T> type, int attempts)
//            throws RuntimeException {
//        while (attempts > 0) {
//            String response = llm.request(prompt);
//
//            Optional<T> instance = fromJson(response, type);
//
//            if (instance.isPresent()) {
//                return instance;
//            }
//
//            attempts--;
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public <T> Optional<T> fromJson(String json, Class<T> type) {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Duration.class, new DurationAdapter())
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//                .create();
//
//        try {
//            T instance = gson.fromJson(json, type);
//            return Optional.of(instance);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
//}
