package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.impl;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.config.OpenAiConfig;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.ChatGptAPIService;

import java.net.URI;

import static ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util.ChatGptConstant.*;

@Service
@RequiredArgsConstructor
public class ChatGptAPIServiceImpl implements ChatGptAPIService {
    private final OpenAiConfig openAiConfig;
    private final RestTemplate restTemplate;

    @Override
    public String putRequestChatGPT(String vacancy) {
        HttpHeaders headers = setHeaders();

        JSONArray messagesArray = new JSONArray();
        JSONObject message = new JSONObject();
        message.put(ROLE, "user");
        message.put(CONTENT,
                TEXT_REQUEST_TO_CHAT_GPT + vacancy);
        messagesArray.put(message);

        JSONObject request = new JSONObject();
        request.put(MODEL, openAiConfig.getTextModel());
        request.put(MESSAGES, messagesArray);
        request.put(TEMPERATURE, openAiConfig.getTemperature());
        request.put(MAX_TOKENS, openAiConfig.getMaxTokens());
        request.put(TOP_P, openAiConfig.getTopP());
        request.put(FREQUENCY_PENALTY, openAiConfig.getFreqPenalty());
        request.put(PRESENCE_PENALTY, openAiConfig.getPresPenalty());

        HttpEntity<String> requestEntity = new HttpEntity<>(request.toString(), headers);

        URI chatGptUrl = URI.create(openAiConfig.getOpenaiUrl());
        ResponseEntity<String> responseEntity = restTemplate.
                postForEntity(chatGptUrl, requestEntity, String.class);
        return getResponseFromChatGPT(responseEntity);
    }

    private String getResponseFromChatGPT(ResponseEntity<String> responseEntity) {
        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = (JSONArray) responseJson.get(CHOICES);
        JSONObject firstChoice = (JSONObject) choices.get(0);
        JSONObject message = firstChoice.getJSONObject(MESSAGE);
        return message.getString(CONTENT);
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, "Bearer " + openAiConfig.getApiToken());
        return headers;
    }
}
