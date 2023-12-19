package ua.bot.telegram.vacancies.telegrambotvacancies.service;

import ua.bot.telegram.vacancies.telegrambotvacancies.dto.VacancyDTO;

import java.util.List;

public interface VacancyFileReaderService {
    List<VacancyDTO> getVacanciesFromFile(String nameFile);
}
