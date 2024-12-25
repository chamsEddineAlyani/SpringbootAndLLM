package com.chatGPT.chat.service.impl;

import com.chatGPT.chat.model.AIRequest;
import com.chatGPT.chat.service.ItineraryAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ItineraryAIServiceImpl implements ItineraryAIService {
    private final WebClient webClient ;
    @Value("${spring.ai.ollama.chat.model}")
    private String aiModel;

    public ItineraryAIServiceImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:11434").build();
    }

    @Override
    public Mono<String> getItinerary(String origin, String destination, int numberOfDays, String preference) {
        String promt = String.format(
                "Generate a %d-day travel itinerary from %s to %s  with a preference of %s .",numberOfDays,origin,destination,preference
        );
        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AIRequest(aiModel,promt))
                .retrieve()
                .bodyToFlux(String.class)
                .map(jsonString->{
                    JsonNode jsonNode = null;
                    try {
                        jsonNode = new ObjectMapper().readTree(jsonString);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return jsonNode.get("response").asText();
                })
                .filter(response -> !response.isEmpty())
                .reduce(String::concat)
                .doOnError(error-> System.err.println("Error during API call"+ error.getMessage()))
                ;
    }
}
