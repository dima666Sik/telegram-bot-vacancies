package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service;

import org.springframework.http.ResponseEntity;

public interface Parser {
    String parseResponse(ResponseEntity<String> responseEntity);
}
