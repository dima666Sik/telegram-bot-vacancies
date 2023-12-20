package ua.bot.telegram.vacancies.telegrambotvacancies.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ua.bot.telegram.vacancies.telegrambotvacancies.dto.VacancyDTO;
import ua.bot.telegram.vacancies.telegrambotvacancies.exceptions.FileWasNotRead;
import ua.bot.telegram.vacancies.telegrambotvacancies.service.VacancyFileReaderService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Log4j2
public class VacancyReaderCSVServiceImpl implements VacancyFileReaderService {
    @Override
    public List<VacancyDTO> getVacanciesFromFile(String fileName) {
        Resource resource = new ClassPathResource(fileName);
        try (InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            CsvToBean<VacancyDTO> csvToBean = new CsvToBeanBuilder<VacancyDTO>(inputStreamReader)
                    .withType(VacancyDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            log.error("File " + fileName + " was not be read!", e);
            throw new FileWasNotRead("File " + fileName + " was not be read!", e);
        }
    }
}
