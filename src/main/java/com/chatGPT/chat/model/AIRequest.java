package com.chatGPT.chat.model;

public record AIRequest(
        String model,
        String prompt
) {
}
