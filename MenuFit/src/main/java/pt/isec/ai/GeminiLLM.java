package pt.isec.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @deprecated Use {@link GroqLLM} instead.
 */
@Deprecated
public class GeminiLLM implements CommonLLM {
    private static GeminiLLM instance = null;

    public static GeminiLLM getInstance() {
        if (instance == null) {
            instance = new GeminiLLM();
        }

        return instance;
    }

    private GeminiLLM() {
        this.apiKey = GeminiLLM.defaultApiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void resetApiKey() {
        this.apiKey = GeminiLLM.defaultApiKey;
    }

    @Override
    public String request(String prompt) throws RuntimeException {
        if (prompt.length() > GeminiLLM.inputTokenLimit) {
            throw new RuntimeException("Input prompt must not exceed " +
                    GeminiLLM.inputTokenLimit + " tokens.");
        }

        try {
            // Create a URL object with the API endpoint
            URI uri = getGeminiFullUri(apiKey);

            // Assemble API payload
            String payload = formatPayload(prompt);

            StringBuilder response = getStringBuilder(uri, payload);

            System.out.println("Response: " + response.toString());

            return parseResponse(response.toString());

        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    private static URI getGeminiFullUri(String apiKey) throws URISyntaxException {
        return new URI(endpoint + "/" + path + "/" + modelCode + ":" + modelOperation + "?" + keyQueryParam + "=" + apiKey);
    }

    private static String formatPayload(String payload) {
        return "{\"contents\":[{\"parts\":[{\"text\":\"" + payload + "\"}]}]}";
    }

    private static StringBuilder getStringBuilder(URI uri, String jsonInputString) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(uri, jsonInputString);

        // Read the response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return response;
    }

    private static HttpURLConnection getHttpURLConnection(URI uri, String jsonInputString) throws IOException {
        URL url = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        // Write JSON data to request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        return connection;
    }

    private static String parseResponse(String response)
            throws RuntimeException {
        try {
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            JsonElement jsonElement = JsonParser.parseString(response);

            return extractContent(jsonElement, new StringBuilder())
                    .toString();
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
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

    private static final String defaultApiKey = "AIzaSyC11UCRVs5allSkZG8PkY85-VTXG21p1MA";

    private static final int inputTokenLimit = 1_048_576;

    private static final int outputTokenLimit = 8_192;

    private static final String endpoint = "https://generativelanguage.googleapis.com";

    private static final String modelCode = "gemini-1.5-flash-latest";

    private static final String modelOperation = "generateContent";

    private static final String path = "v1beta/models";

    private static final String keyQueryParam = "key";

    private static final String content = "text";

    private String apiKey;
}
