package ua.bot.telegram.vacancies.telegrambotvacancies.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramBotCommand {

    START("/start");

    private final String command;
}
