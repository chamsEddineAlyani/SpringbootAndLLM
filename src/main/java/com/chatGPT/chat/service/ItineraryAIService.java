package com.chatGPT.chat.service;

import reactor.core.publisher.Mono;

public interface ItineraryAIService {
    Mono<String> getItinerary(String origin ,String destination , int numberOfDays,String preference);
}
