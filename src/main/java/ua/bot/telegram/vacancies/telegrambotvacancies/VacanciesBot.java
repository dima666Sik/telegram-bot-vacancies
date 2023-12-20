package ua.bot.telegram.vacancies.telegrambotvacancies;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.SendMessageWasNotExecuted;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.SetCommandWasNotExecuted;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.UnsupportedLvlVacancies;
import ua.bot.telegram.vacancies.telegrambotvacancies.service.VacancyService;
import ua.bot.telegram.vacancies.telegrambotvacancies.utils.UtilsCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ua.bot.telegram.vacancies.telegrambotvacancies.enums.TelegramBotCommand.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class VacanciesBot extends TelegramLongPollingBot {
    private final TelegramBotMenu telegramBotMenu;
    private final VacancyService vacancyService;
    private final TelegramBotMenuCommand telegramBotMenuCommand;
    private final TelegramBotConfig telegramBotConfig;

    // key: chat id, value: lvl
    private final Map<Long, String> mapLastShowLvl = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            log.info("start execute set commands!");
            this.execute(new SetMyCommands(telegramBotMenuCommand.getBotCommandList(),
                    new BotCommandScopeDefault(), null));
            log.info("successful execute set commands!");
        } catch (TelegramApiException e) {
            log.error("execute set commands was wrong!");
            throw new SetCommandWasNotExecuted("Execute set commands was wrong!",e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().getText().equals(START.getCommand())) {
                handleMessageReceived(update);
            } else if (update.getMessage().getText().equals(HELP.getCommand())){
                handleCallHelp(update);
            }
        }
        if (update.hasCallbackQuery()) {
            handleCallQuery(update);
        }
    }

    private void handleCallHelp(Update update) {
        sendMessage(update.getMessage().getChatId(),
                UtilsCommand.HELP_TEXT,
                Optional.empty());
    }

    private void handleCallQuery(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        if (callBackData.equals("Show Junior Vacancies")) {
            showJuniorVacanciesUpdate(update);
        } else if (callBackData.equals("Show Middle Vacancies")) {
            showMiddleVacanciesUpdate(update);
        } else if (callBackData.equals("Show Senior Vacancies")) {
            showSeniorVacanciesUpdate(update);
        } else if (callBackData.equals("Back to vacancies")) {
            handleBackToVacancies(update);
        } else if (callBackData.equals("Back to start menu")) {
            handleBackToStartMenu(update);
        } else if (callBackData.startsWith("vacId=")) {
            Long id = Long.parseLong(callBackData.split("=")[1]);
            showVacancyDescription(id, update);
        }
    }

    private void handleBackToStartMenu(Update update) {
        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Please choose your level:",
                Optional.of(telegramBotMenu.getMenuLevelUserQualification()));
    }

    private void handleBackToVacancies(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String lvl = mapLastShowLvl.get(chatId);

        switch (lvl) {
            case "junior" -> showJuniorVacanciesUpdate(update);
            case "middle" -> showMiddleVacanciesUpdate(update);
            case "senior" -> showSeniorVacanciesUpdate(update);
            default -> throw new UnsupportedLvlVacancies("You choose unsupported lvl vacancies!");
        }
    }

    private void showVacancyDescription(Long id, Update update) {
        String shortDescription = vacancyService.getVacancyById(id).getShortDescription();
        String longDescription = vacancyService.getVacancyById(id).getLongDescription();
        String company = vacancyService.getVacancyById(id).getCompany();
        String salary = vacancyService.getVacancyById(id).getSalary();
        String link = vacancyService.getVacancyById(id).getLink();

        sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Short description: " + shortDescription + "\n\n" +
                        "Long description: " + longDescription + "\n\n" +
                        "Company: " + company + "\n\n" +
                        "Salary: " + salary + "\n\n" +
                        "Link: " + link + "\n\n",
                Optional.of(telegramBotMenu.getBackToVacanciesMenu()));
    }

    private void showJuniorVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage(chatId,
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getJuniorVacanciesMenu()));
        mapLastShowLvl.put(chatId, "junior");
    }

    private void showMiddleVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage(chatId,
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getMiddleVacanciesMenu()));
        mapLastShowLvl.put(chatId, "middle");
    }

    private void showSeniorVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage(chatId,
                "Please choose vacancy:",
                Optional.of(telegramBotMenu.getSeniorVacanciesMenu()));
        mapLastShowLvl.put(chatId, "senior");
    }

    private void handleMessageReceived(Update update) {
        sendMessage(update.getMessage().getChatId(),
                "Welcome to the " + telegramBotConfig.getUsername() + "! Please choose your level:",
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
            throw new SendMessageWasNotExecuted("Execute send message was wrong!",e);
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getTokenAccess();
    }
}
