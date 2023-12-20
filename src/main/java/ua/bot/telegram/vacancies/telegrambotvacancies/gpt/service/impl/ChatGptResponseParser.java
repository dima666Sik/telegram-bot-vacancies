package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.Parser;

import java.net.URI;

import static ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util.ChatGptConstant.*;

@Service
public class ChatGptResponseParser implements Parser {
    @Override
    public String parseResponse(ResponseEntity<String> responseEntity) {
        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = (JSONArray) responseJson.get(CHOICES);
        JSONObject firstChoice = (JSONObject) choices.get(0);
        JSONObject message = firstChoice.getJSONObject(MESSAGE);
        return message.getString(CONTENT);
    }
}
