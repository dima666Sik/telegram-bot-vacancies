package ua.bot.telegram.vacancies.telegrambotvacancies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.bot.telegram.vacancies.telegrambotvacancies.service.VacancyService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBotMenu {
    private final VacancyService vacancyService;

    private ReplyKeyboard createMenuReplyKeyboard(List<InlineKeyboardButton> rowInlineKeyboardButtons) {

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
        List<InlineKeyboardButton> rowInlineKeyboardButtons = new ArrayList<>();
        vacancyService.getVacanciesByLvl("junior").forEach(v -> rowInlineKeyboardButtons.add(getBtnKeyboard(v.getTitle(), "vacId=" + v.getId())));
        return createMenuReplyKeyboard(rowInlineKeyboardButtons);
    }

    public ReplyKeyboard getMiddleVacanciesMenu() {
        List<InlineKeyboardButton> rowInlineKeyboardButtons = new ArrayList<>();
        vacancyService.getVacanciesByLvl("middle").forEach(v -> rowInlineKeyboardButtons.add(getBtnKeyboard(v.getTitle(), "vacId=" + v.getId())));
        return createMenuReplyKeyboard(rowInlineKeyboardButtons);
    }

    public ReplyKeyboard getSeniorVacanciesMenu() {
        List<InlineKeyboardButton> rowInlineKeyboardButtons = new ArrayList<>();
        vacancyService.getVacanciesByLvl("senior").forEach(v -> rowInlineKeyboardButtons.add(getBtnKeyboard(v.getTitle(), "vacId=" + v.getId())));
        return createMenuReplyKeyboard(rowInlineKeyboardButtons);
    }

    private InlineKeyboardButton getBtnKeyboard(String textBtn, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(textBtn);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }

    public ReplyKeyboard getBackToVacanciesMenu() {
        return createMenuReplyKeyboard(List.of(getBtnKeyboard("Back to vacancies", "Back to vacancies"),
                getBtnKeyboard("Back to start menu", "Back to start menu")));
    }
}
