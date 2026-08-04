package expense_tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/"
                    + "gemini-3.6-flash:generateContent";


    public String generateContent(String prompt) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);


        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "text",
                                                prompt
                                        )
                                )
                        )
                )
        );


        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(
                        requestBody,
                        headers
                );


        ResponseEntity<Map> response =
                restTemplate.exchange(
                        GEMINI_URL,
                        HttpMethod.POST,
                        entity,
                        Map.class
                );


        return extractText(response.getBody());
    }


    private String extractText(Map body) {

        List<Map<String, Object>> candidates =
                (List<Map<String, Object>>)
                        body.get("candidates");

        if (candidates == null || candidates.isEmpty()) {
            return "No AI response generated.";
        }

        Map<String, Object> content =
                (Map<String, Object>)
                        candidates.get(0).get("content");

        List<Map<String, Object>> parts =
                (List<Map<String, Object>>)
                        content.get("parts");

        return (String) parts.get(0).get("text");
    }
}