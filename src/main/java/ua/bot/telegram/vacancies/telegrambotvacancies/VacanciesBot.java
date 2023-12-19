package ua.bot.telegram.vacancies.telegrambotvacancies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.SendMessageWasNotExecuted;

import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class VacanciesBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token.access}")
    private String tokenAccess;
    @Value("${telegram.bot.username}")
    private String username;

    private final TelegramBotMenu telegramBotMenu;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            handleMessageReceived(update);
        }
        if (update.hasCallbackQuery()) {
            handleCallQuery(update);
        }
    }

    private void handleCallQuery(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        if (callBackData.equals("Show Junior Vacancies")) {
            showJuniorVacanciesUpdate(update);
        } else if (callBackData.equals("Show Middle Vacancies")) {
            showMiddleVacanciesUpdate(update);
        } else if (callBackData.equals("Show Senior Vacancies")) {
            showSeniorVacanciesUpdate(update);
        } else if (callBackData.startsWith("vacId=")) {
            String id = callBackData.substring(callBackData.length() - 1);
            showVacancyDescription(id, update);
        }
    }

    private void showVacancyDescription(String id, Update update) {
        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Vacancy description with id = " + id,
                Optional.empty());
    }

    private void showJuniorVacanciesUpdate(Update update) {
        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getJuniorVacanciesMenu()));
    }

    private void showMiddleVacanciesUpdate(Update update) {
        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getMiddleVacanciesMenu()));
    }

    private void showSeniorVacanciesUpdate(Update update) {
        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getSeniorVacanciesMenu()));
    }

    private void handleMessageReceived(Update update) {
        sendMessage(update.getMessage().getChatId(),
                "Welcome to the " + username + "! Please choose your level:",
                Optional.of(telegramBotMenu.getMenuLevelUserQualification()));
    }

    private void sendMessage(Long id, String textMessage, Optional<ReplyKeyboard> replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(textMessage);

        replyKeyboard.ifPresent(sendMessage::setReplyMarkup);

        try {
            log.info("start execute send message!");
            execute(sendMessage);
            log.info("successful execute send message!");
        } catch (TelegramApiException e) {
            log.error("execute send message was wrong!");
            throw new SendMessageWasNotExecuted(e);
        }
    }


    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return tokenAccess;
    }
}
