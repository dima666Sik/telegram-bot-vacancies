package ua.bot.telegram.vacancies.telegrambotvacancies.bot.service;

import ua.bot.telegram.vacancies.telegrambotvacancies.bot.dto.VacancyDTO;

import java.util.List;

public interface VacancyService {
    List<VacancyDTO> getVacanciesByLvl(String lvl);
    VacancyDTO getVacancyById(Long id);
}
