package ua.bot.telegram.vacancies.telegrambotvacancies;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class TelegramBotMenu {
    public ReplyKeyboard createMenuReplyKeyboard(List<InlineKeyboardButton> rowInlineKeyboardButtons) {

        InlineKeyboardMarkup keyboardMarkup
                = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(rowInlineKeyboardButtons));

        return keyboardMarkup;
    }

    public ReplyKeyboard getMenuLevelUserQualification() {
        return createMenuReplyKeyboard(List.of(getBtnKeyboard("Junior", "Show Junior Vacancies"),
                getBtnKeyboard("Middle", "Show Middle Vacancies"),
                getBtnKeyboard("Senior", "Show Senior Vacancies")));
    }

    public ReplyKeyboard getJuniorVacanciesMenu() {
        return createMenuReplyKeyboard(List.of(getBtnKeyboard("Junior Java developer at MA", "vacId=1"),
                getBtnKeyboard("Junior Java developer at Google", "vacId=2")));
    }

    public ReplyKeyboard getMiddleVacanciesMenu() {
        return createMenuReplyKeyboard(List.of(getBtnKeyboard("Middle Java developer at MA", "vacId=3"),
                getBtnKeyboard("Middle Java developer at Google", "vacId=4")));
    }

    public ReplyKeyboard getSeniorVacanciesMenu() {
        return createMenuReplyKeyboard(List.of(getBtnKeyboard("Senior Java developer at MA", "vacId=5"),
                getBtnKeyboard("Senior Java developer at Google", "vacId=6")));
    }

    public InlineKeyboardButton getBtnKeyboard(String textBtn, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(textBtn);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }
}
