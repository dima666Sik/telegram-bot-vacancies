package ua.bot.telegram.vacancies.telegrambotvacancies;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBotMenu {
    public ReplyKeyboard getMenuLevelUserQualification() {
        List<InlineKeyboardButton> rowBtnQualificationUsers
                = new ArrayList<>();

        InlineKeyboardButton btnJunior = getBtnKeyboard("Junior", "Show Junior Vacancies");
        InlineKeyboardButton btnMiddle = getBtnKeyboard("Middle", "Show Middle Vacancies");
        InlineKeyboardButton btnSenior = getBtnKeyboard("Senior", "Show Senior Vacancies");

        rowBtnQualificationUsers.add(btnJunior);
        rowBtnQualificationUsers.add(btnMiddle);
        rowBtnQualificationUsers.add(btnSenior);

        InlineKeyboardMarkup keyboardMarkup
                = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(rowBtnQualificationUsers));

        return keyboardMarkup;
    }

    public ReplyKeyboard getJuniorVacanciesMenu() {
        List<InlineKeyboardButton> rowBtnJuniorVacancies
                = new ArrayList<>();

        InlineKeyboardButton btnJuniorMA
                = getBtnKeyboard("Junior Java developer at MA", "vacId=1");
        InlineKeyboardButton btnJuniorGoogle
                = getBtnKeyboard("Junior Java developer at Google", "vacId=2");

        rowBtnJuniorVacancies.add(btnJuniorMA);
        rowBtnJuniorVacancies.add(btnJuniorGoogle);

        InlineKeyboardMarkup keyboardMarkup
                = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(rowBtnJuniorVacancies));

        return keyboardMarkup;
    }
    public InlineKeyboardButton getBtnKeyboard(String textBtn, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(textBtn);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }
}
