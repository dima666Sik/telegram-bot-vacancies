package ua.bot.telegram.vacancies.telegrambotvacancies.bot.service;

import ua.bot.telegram.vacancies.telegrambotvacancies.bot.dto.VacancyDTO;

import java.util.List;

public interface VacancyFileReaderService {
    List<VacancyDTO> getVacanciesFromFile(String nameFile);
}
