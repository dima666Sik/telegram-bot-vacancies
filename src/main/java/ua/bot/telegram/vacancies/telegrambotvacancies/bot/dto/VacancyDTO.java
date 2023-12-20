package ua.bot.telegram.vacancies.telegrambotvacancies.bot.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDTO {
    @CsvBindByName(column = "Id")
    private Long id;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Short description")
    private String shortDescription;
    @CsvBindByName(column = "Long description")
    private String longDescription;
    @CsvBindByName(column = "Company")
    private String company;
    @CsvBindByName(column = "Salary")
    private String salary;
    @CsvBindByName(column = "Link")
    private String link;
}
