package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class OpenAiConfig {
    @Value("${openai.api.url}")
    private String openaiUrl;
    @Value("${openai.model}")
    private String textModel;
    @Value("${openai.temperature}")
    private int temperature;
    @Value("${openai.max-tokens}")
    private int maxTokens;
    @Value("${openai.top-p}")
    private int topP;
    @Value("${openai.frequency-penalty}")
    private int freqPenalty;
    @Value("${openai.presence-penalty}")
    private int presPenalty;
    @Value("${openai.token.access}")
    private String apiToken;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
