package ua.bot.telegram.vacancies.telegrambotvacancies.service;

import ua.bot.telegram.vacancies.telegrambotvacancies.dto.VacancyDTO;

import java.util.List;

public interface VacancyService {
    List<VacancyDTO> getVacanciesByLvl(String lvl);
    VacancyDTO getVacancyById(Long id);
}
