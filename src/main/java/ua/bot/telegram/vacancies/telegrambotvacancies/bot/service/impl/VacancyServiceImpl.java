package ua.bot.telegram.vacancies.telegrambotvacancies.bot.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.dto.VacancyDTO;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.service.VacancyFileReaderService;
import ua.bot.telegram.vacancies.telegrambotvacancies.bot.service.VacancyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyFileReaderService vacancyFileReaderService;
    private final Map<Long, VacancyDTO> vacancyDTOMap = new HashMap<>();
    @Value("${file.name.vacancies}")
    private String fileNameWithVacancies;

    @PostConstruct
    public void init() {
        List<VacancyDTO> vacancyDTOList = vacancyFileReaderService.getVacanciesFromFile(fileNameWithVacancies);
        vacancyDTOList.forEach(v -> vacancyDTOMap.put(v.getId(), v));
    }

    @Override
    public List<VacancyDTO> getVacanciesByLvl(String lvl) {
        Predicate<VacancyDTO> predicate = (vacancy -> vacancy.getTitle().toLowerCase()
                .contains(lvl.toLowerCase()));
        return vacancyDTOMap
                .values()
                .stream()
                .filter(predicate)
                .toList();
    }

    public VacancyDTO getVacancyById(Long id) {
        return vacancyDTOMap.get(id);
    }
}
