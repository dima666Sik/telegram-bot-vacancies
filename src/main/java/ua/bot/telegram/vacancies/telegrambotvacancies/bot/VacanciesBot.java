package ua.bot.telegram.vacancies.telegrambotvacancies.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.bot.telegram.vacancies.telegrambotvacancies.gpt.service.ChatGptAPIService;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.config.TelegramBotConfig;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.exceptions.SendMessageWasNotExecuted;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.exceptions.SetCommandWasNotExecuted;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.exceptions.UnsupportedLvlVacancies;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.service.VacancyService;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.util.BotCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ua.bot.telegram.vacancies.telegrambotvacancies.bot.enums.TelegramBotCommand.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class VacanciesBot extends TelegramLongPollingBot {
    private final TelegramBotMenu telegramBotMenu;
    private final VacancyService vacancyService;
    private final TelegramBotMenuCommand telegramBotMenuCommand;
    private final TelegramBotConfig telegramBotConfig;
    private final ChatGptAPIService chatGptAPIService;
    private SendMessage sendMessage;

    // key: chat id, value: lvl
    private final Map<Long, String> mapLastShowLvl = new HashMap<>();
    private final Map<Long, String> mapLastShowVacancy = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            log.info("start execute set commands!");
            this.execute(new SetMyCommands(telegramBotMenuCommand.getBotCommandList(),
                    new BotCommandScopeDefault(), null));
            log.info("successful execute set commands!");
        } catch (TelegramApiException e) {
            log.error("execute set commands was wrong!");
            throw new SetCommandWasNotExecuted("Execute set commands was wrong!", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().getText().equals(START.getCommand())) {
                handleMessageReceived(update);
            } else if (update.getMessage().getText().equals(HELP.getCommand())) {
                handleCallHelp(update);
            }
        }
        if (update.hasCallbackQuery()) {
            handleCallQuery(update);
        }
    }

    private void handleCallHelp(Update update) {
        basicSettingSendMessage(update.getMessage().getChatId(),
                BotCommand.HELP_TEXT);
        sendMessage(Optional.empty());
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
        } else if (callBackData.equals("Generate With ChatGPT cover letter")) {
            handleGenerateWithGptCoverLetter(update);
        } else if (callBackData.startsWith("vacId=")) {
            Long id = Long.parseLong(callBackData.split("=")[1]);
            showVacancyDescription(id, update);
        }
    }

    private void handleGenerateWithGptCoverLetter(Update update) {
        if (!mapLastShowVacancy.isEmpty()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String resultGenerateGpt = chatGptAPIService.putRequestChatGPT(mapLastShowVacancy.get(chatId));
            basicSettingSendMessage(chatId,
                    resultGenerateGpt);
            sendMessage(
                    Optional.of(telegramBotMenu.getBackToVacanciesMenu()));
        }
    }

    private void handleBackToStartMenu(Update update) {
        basicSettingSendMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Please choose your level:");
        sendMessage(Optional.of(telegramBotMenu.getMenuLevelUserQualification()));
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
        String title = vacancyService.getVacancyById(id).getTitle();
        String shortDescription = vacancyService.getVacancyById(id).getShortDescription();
        String longDescription = vacancyService.getVacancyById(id).getLongDescription();
        String company = vacancyService.getVacancyById(id).getCompany();
        String salary = vacancyService.getVacancyById(id).getSalary();
        String link = vacancyService.getVacancyById(id).getLink();

        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        String vacancyText = """
                *Title:* %s \n\n
                *Short description:* %s \n\n
                *Long description:* %s \n\n
                *Company:* %s \n\n
                *Salary:* %s \n\n
                *Link:* [%s](%s)
                """.formatted(
                escapeReservedMarkdownCharacters(title),
                escapeReservedMarkdownCharacters(shortDescription),
                escapeReservedMarkdownCharacters(longDescription),
                escapeReservedMarkdownCharacters(company),
                salary.isBlank() || salary.equals("-") ? "Salary not specified" : escapeReservedMarkdownCharacters(salary),
                escapeReservedMarkdownCharacters("Click in order to get more details!"),
                escapeReservedMarkdownCharacters(link));

        basicSettingSendMessage(chatId,
                vacancyText);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage(
                Optional.of(telegramBotMenu.getBackToVacanciesMenu()));

        mapLastShowVacancy.put(chatId, vacancyText);
    }

    private String escapeReservedMarkdownCharacters(String str) {
        return str.replace("*", "\\*")
                .replace("_", "\\_")
                .replace("-", "\\-")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("~", "\\~")
                .replace("`", "\\`")
                .replace(">", "\\>")
                .replace("#", "\\#")
                .replace("+", "\\+")
                .replace(".", "\\.")
                .replace("!", "\\!");

    }

    private void showJuniorVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        basicSettingSendMessage(chatId,
                "Please choose vacancy:");
        sendMessage(Optional.of(telegramBotMenu.getJuniorVacanciesMenu()));
        mapLastShowLvl.put(chatId, "junior");
    }

    private void showMiddleVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        basicSettingSendMessage(chatId,
                "Please choose vacancy:");
        sendMessage(Optional.of(telegramBotMenu.getMiddleVacanciesMenu()));
        mapLastShowLvl.put(chatId, "middle");
    }

    private void showSeniorVacanciesUpdate(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        basicSettingSendMessage(chatId,
                "Please choose vacancy:");
        sendMessage(
                Optional.of(telegramBotMenu.getSeniorVacanciesMenu()));
        mapLastShowLvl.put(chatId, "senior");
    }

    private void handleMessageReceived(Update update) {
        basicSettingSendMessage(update.getMessage().getChatId(),
                "Welcome to the " + telegramBotConfig.getUsername() + "! Please choose your level:");
        sendMessage(
                Optional.of(telegramBotMenu.getMenuLevelUserQualification()));
    }

    private void basicSettingSendMessage(Long id, String textMessage) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(textMessage);
    }

    private void sendMessage(Optional<ReplyKeyboard> replyKeyboard) {

        replyKeyboard.ifPresent(sendMessage::setReplyMarkup);

        try {
            log.info("start execute send message!");
            execute(sendMessage);
            log.info("successful execute send message!");
        } catch (TelegramApiException e) {
            log.error("execute send message was wrong!");
            throw new SendMessageWasNotExecuted("Execute send message was wrong!", e);
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
