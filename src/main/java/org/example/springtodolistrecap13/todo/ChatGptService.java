package org.example.springtodolistrecap13.todo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ChatGPTService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public ChatGPTService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/completions").build();
    }

    public String checkSpelling(String text) {
        try {
            String response = this.webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"Please check the spelling of this text: " + text + "\"}]}") // Der Text, den wir prüfen möchten
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // block() macht die Anfrage synchron, aber in einer echten Anwendung könntest du async verwenden.

            return response;
        } catch (WebClientResponseException e) {
            return "Error while calling the OpenAI API: " + e.getMessage();
        }
    }
}
