package ua.bot.telegram.vacancies.telegrambotvacancies.bot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramBotCommand {

    START("/start","Start main work application."),
    HELP("/help","It is help command, can show u some various resolves often issues.");

    private final String command;
    private final String description;
}
