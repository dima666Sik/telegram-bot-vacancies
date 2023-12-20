package ua.bot.telegram.vacancies.telegrambotvacancies.bot.exceptions;

public class SendMessageWasNotExecuted extends RuntimeException{
    public SendMessageWasNotExecuted() {
    }

    public SendMessageWasNotExecuted(String message) {
        super(message);
    }

    public SendMessageWasNotExecuted(String message, Throwable cause) {
        super(message, cause);
    }

    public SendMessageWasNotExecuted(Throwable cause) {
        super(cause);
    }
}
