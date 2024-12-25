package com.chatGPT.chat.controller;

import com.chatGPT.chat.service.ItineraryAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {
    private final ItineraryAIService itineraryAIService;
    @GetMapping
    public Mono<String> getItinerary(
            @RequestParam String origin,
            @RequestParam String destination ,
            @RequestParam int numberOfDays,
            @RequestParam String preference)
    {
        return itineraryAIService.getItinerary(origin,destination,numberOfDays,preference);
    }
}