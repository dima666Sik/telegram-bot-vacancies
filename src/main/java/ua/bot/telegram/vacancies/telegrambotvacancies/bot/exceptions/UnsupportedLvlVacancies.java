package ua.bot.telegram.vacancies.telegrambotvacancies.bot.exceptions;

public class UnsupportedLvlVacancies extends RuntimeException {
    public UnsupportedLvlVacancies() {
    }

    public UnsupportedLvlVacancies(String message) {
        super(message);
    }

    public UnsupportedLvlVacancies(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedLvlVacancies(Throwable cause) {
        super(cause);
    }
}
