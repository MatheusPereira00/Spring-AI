package com.bookstore.ai.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BokkstoreAssistantController {

    private final OpenAiChatClient openAiChatClient;

    public BokkstoreAssistantController(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    // RETURN INFORMATION
    @GetMapping(path = "/informations")
    public String bookstoreChat0(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
        return openAiChatClient.call(message);
    }

    // RETURN MORE COMPLETE
    @GetMapping(path = "/informations")
    public ChatResponse bookstoreChat1(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
        return openAiChatClient.call(new Prompt(message));
    }

    // RETURN FLUX STRING
    @GetMapping(path = "/stream/informations")
    public Flux<String> bookstoreStream(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
        return openAiChatClient.stream(message);
    }

    // RETURN FLUX CHATRESPONSE
    @GetMapping(path = "/stream/informations")
    public Flux<ChatResponse> bookstoreChatResponse(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
        return openAiChatClient.stream(new Prompt(message));
    }

    // RETURN REVIEW
    @GetMapping(path = "/review")
    public String bookstoreReview(@RequestParam(value = "book",
            defaultValue = "Dom Quixote") String book) {

        PromptTemplate promptTemplate = new PromptTemplate("""
                
                Por favor, me forneça um breve resumo do livro {book}
                e também a biografia de seu autor.
                
                """);
        promptTemplate.add("book", book);
        return this.openAiChatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

}
