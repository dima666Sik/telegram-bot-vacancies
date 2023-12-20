package ua.bot.telegram.vacancies.telegrambotvacancies.exceptions;

public class SetCommandWasNotExecuted extends RuntimeException{
    public SetCommandWasNotExecuted() {
    }

    public SetCommandWasNotExecuted(String message) {
        super(message);
    }

    public SetCommandWasNotExecuted(String message, Throwable cause) {
        super(message, cause);
    }

    public SetCommandWasNotExecuted(Throwable cause) {
        super(cause);
    }
}
