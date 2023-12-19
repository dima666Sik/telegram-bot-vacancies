package ua.bot.telegram.vacancies.telegrambotvacancies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.SendMessageWasNotExecuted;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class VacanciesBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token.access}")
    private String tokenAccess;
    @Value("${telegram.bot.username}")
    private String username;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            Message message = update.getMessage();

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Welcome to the \{username}! Please choose your level:");
            sendMessage.setReplyMarkup(getMenuLevelUserQualification());
            try {
                log.info("start execute send message!");
                execute(sendMessage);
                log.info("successful execute send message!");
            } catch (TelegramApiException e) {
                log.error("execute send message was wrong!");
                throw new SendMessageWasNotExecuted(e);
            }

        }
    }

    private ReplyKeyboard getMenuLevelUserQualification() {
        List<InlineKeyboardButton> rowBtnQualificationUsers
                = new ArrayList<>();

        InlineKeyboardButton btnJunior = getBtnQualificationUser("Junior", "Show Junior Vacancies");
        InlineKeyboardButton btnMiddle = getBtnQualificationUser("Middle", "Show Middle Vacancies");
        InlineKeyboardButton btnSenior = getBtnQualificationUser("Senior", "Show Senior Vacancies");

        rowBtnQualificationUsers.add(btnJunior);
        rowBtnQualificationUsers.add(btnMiddle);
        rowBtnQualificationUsers.add(btnSenior);

        InlineKeyboardMarkup keyboardMarkup
                = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(rowBtnQualificationUsers));

        return keyboardMarkup;
    }

    private InlineKeyboardButton getBtnQualificationUser(String junior, String showJuniorVacancies) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(junior);
        inlineKeyboardButton.setCallbackData(showJuniorVacancies);
        return inlineKeyboardButton;
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
