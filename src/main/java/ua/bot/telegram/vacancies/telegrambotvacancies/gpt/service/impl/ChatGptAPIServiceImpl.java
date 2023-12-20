package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.config.OpenAiConfig;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.ChatGptAPIService;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.ChatGptRequestBuilder;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.Parser;

import static ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util.ChatGptConstant.AUTHORIZATION;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatGptAPIServiceImpl implements ChatGptAPIService {
    private final OpenAiConfig openAiConfig;
    private final RestTemplate restTemplate;
    private final ChatGptRequestBuilder requestBuilder;
    private final Parser responseParser;

    @Override
    public String putRequestChatGPT(String vacancy) {
        HttpHeaders headers = setHeaders();
        log.info("Start build request");
        HttpEntity<String> requestEntity = requestBuilder.buildRequest(openAiConfig, vacancy, headers);
        log.info("Request was build");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(openAiConfig.getOpenaiUrl(), requestEntity, String.class);
        log.info("Response was got");
        return responseParser.parseResponse(responseEntity);
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION, "Bearer " + openAiConfig.getApiToken());
        return headers;
    }
}
