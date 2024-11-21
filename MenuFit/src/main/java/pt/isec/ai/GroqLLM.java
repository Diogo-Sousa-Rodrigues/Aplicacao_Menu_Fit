package pt.isec.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GroqLLM  implements CommonLLM{
    private static GroqLLM instance = null;

    public static GroqLLM getInstance() {
        if (instance == null) {
            instance = new GroqLLM();
        }

        return instance;
    }

    private GroqLLM() {}

    @Override
    public String request(String prompt)
            throws RuntimeException {
        String payload = formatPayload(prompt);

        HttpRequest httpRequest = getHttpRequest(payload, apiKey);

        try (HttpClient httpClient = HttpClient.newHttpClient()){

            String response = sendHttpRequest(httpClient, httpRequest);

            return parseResponse(response);
        } catch (Exception e) {

            throw new RuntimeException("Unable to send request: " + e);
        }
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private static String formatPayload(String payload) {
        JsonObject jsonObject = new JsonObject();
        JsonArray messages = new JsonArray();
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("role", "user");
        jsonElement.addProperty("content", payload);
        messages.add(jsonElement);
        jsonObject.add("messages", messages);
        jsonObject.addProperty("model", "llama3-8b-8192");

        return jsonObject.toString();
    }

    private static HttpRequest getHttpRequest(String payload, String apiKey) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
    }

    private static String sendHttpRequest(HttpClient httpClient, HttpRequest httpRequest)
            throws RuntimeException {
        try {
            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            if (statusCode == 200) {
                return response.body();
            }

            throw new RuntimeException("Request failure: " + statusCode);

        } catch (Exception e) {
            throw new RuntimeException("sendHttpRequest: " + e);
        }
    }

    private static String parseResponse(String response)
            throws RuntimeException {
        try {
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            JsonElement jsonElement = JsonParser.parseString(response);

            return extractContent(jsonElement, new StringBuilder())
                    .toString();
        } catch (Exception e) {
            throw new RuntimeException("parseResponse: " + e);
        }
    }

    private static StringBuilder extractContent(JsonElement element, StringBuilder current) {
        if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();

            for (String key : jsonObject.keySet()) {
                if (key.equals(content) && jsonObject.get(key).isJsonPrimitive()) {
                    current.append(jsonObject.get(key).getAsString());
                } else {
                    extractContent(jsonObject.get(key), current);
                }
            }
        } else if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement arrayElement : jsonArray) {
                extractContent(arrayElement, current);
            }
        }

        return current;
    }

    private static final String endpoint = "https://api.groq.com/openai/v1/chat/completions";
    private static final String content = "content";

    private String apiKey;
}
