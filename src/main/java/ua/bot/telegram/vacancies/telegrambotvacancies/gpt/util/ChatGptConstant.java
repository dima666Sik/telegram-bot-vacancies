package ua.bot.telegram.vacancies.telegrambotvacancies.gpt.util;

public class ChatGptConstant {
    public static final String AUTHORIZATION = "Authorization";
    public static final String MODEL = "model";
    public static final String MESSAGES = "messages";
    public static final String ROLE = "role";
    public static final String CONTENT = "content";
    public static final String TEMPERATURE = "temperature";
    public static final String MAX_TOKENS = "max_tokens";
    public static final String TOP_P = "top_p";
    public static final String FREQUENCY_PENALTY = "frequency_penalty";
    public static final String PRESENCE_PENALTY = "presence_penalty";
    public static final String CHOICES = "choices";
    public static final String MESSAGE = "message";
    public static final String TEXT_REQUEST_TO_CHAT_GPT = """
            generate me a cover letter for the vacancy, I am a Java developer without experience,
            I know Java Core well, I know how to work with databases, 
            I know how to create web applications, I know how to work with Api.  
            I have good knowledge of Hibernate and Spring frameworks.
            
            Vacancy:
            """;

    private ChatGptConstant() {
    }
}
