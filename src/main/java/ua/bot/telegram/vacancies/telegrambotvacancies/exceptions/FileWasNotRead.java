package ua.bot.telegram.vacancies.telegrambotvacancies.exceptions;

public class FileWasNotRead extends RuntimeException {
    public FileWasNotRead() {
    }

    public FileWasNotRead(String message) {
        super(message);
    }

    public FileWasNotRead(String message, Throwable cause) {
        super(message, cause);
    }

    public FileWasNotRead(Throwable cause) {
        super(cause);
    }
}
