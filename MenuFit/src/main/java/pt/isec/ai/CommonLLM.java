package pt.isec.ai;

public interface CommonLLM {
    String request(String prompt) throws RuntimeException;
    void setApiKey(String apiKey);
}
