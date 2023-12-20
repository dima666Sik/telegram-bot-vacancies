package ua.bot.telegram.vacancies.telegrambotvacancies.bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramBotConfig {
    @Value("${telegram.bot.token.access}")
    private String tokenAccess;
    @Value("${telegram.bot.username}")
    private String username;
}
