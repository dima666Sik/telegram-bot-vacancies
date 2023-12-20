package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.config.OpenAiConfig;

public interface ChatGptRequestBuilder {
    HttpEntity<String> buildRequest(OpenAiConfig openAiConfig, String vacancy, HttpHeaders headers);
}
