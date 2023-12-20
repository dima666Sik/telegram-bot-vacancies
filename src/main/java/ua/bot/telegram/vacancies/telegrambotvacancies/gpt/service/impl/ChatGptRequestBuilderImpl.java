package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.config.OpenAiConfig;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.ChatGptRequestBuilder;

import static ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util.ChatGptConstant.*;
import static ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util.ChatGptConstant.PRESENCE_PENALTY;

@Service
public class ChatGptRequestBuilderImpl implements ChatGptRequestBuilder {
    @Override
    public HttpEntity<String> buildRequest(OpenAiConfig openAiConfig, String vacancy, HttpHeaders headers) {

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

        return new HttpEntity<>(request.toString(), headers);
    }
}
