# Telegram bot vacancies

---

## List of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Features](#features)
- [Usage](#usage)
- [Author Info](#author-info)

---

## Description

### Preview, I hope you enjoy it 😊

Welcome to `telegram-bot-vacancies` application.
This project is built using Java 21, telegram bot api, openai api.<br>
I am using chatGPT for generation of a cover letter to last chosen vacancy by user. 
All technologies you can see in the part of this README.md 
under the heading [Technologies](#technologies).

---

## Technologies
- `Telegram Bot API`: The Telegram Bot API is utilized to integrate Telegram bot functionalities into the project. This enables efficient communication and interaction with users on the Telegram platform.
- `OpenAI API`: We integrate the OpenAI API to generate personalized cover letters for the last chosen vacancy by the user.
- `opencsv:` The opencsv library is employed to efficiently read files containing vacancy information.
- `lombok`: Lombok is used to reduce boilerplate code in the project. With its annotations, Lombok simplifies the codebase, making it more concise and readable while maintaining the necessary functionality.
- `Spring Boot`: The project is built on the Spring Boot framework, which offers a robust and streamlined development experience. Spring Boot's features, including dependency management and auto-configuration, contribute to the creation of scalable and maintainable applications.
---

## Features
**You can:**
- ☑️️ Using command Telegram bot;
   - ☑️ `/start`;
   - ☑️ `/help`;
- ☑️ Choose lvl searching vacancies;
   - ☑️ **Junior**;
   - ☑️ **Middle**;
   - ☑️ **Senior**;
- ☑️ Choose certain vacancy from all those was read from .csv file;
- ☑️ Watch on the vacancy in detail;
- ☑️ Generate of a cover letter for the last chosen vacancy by the user;
- ☑️ Back to the choice vacancies/Back to choose lvl searching vacancies;
---

## Usage

1. I would you recommended to clone my project from the GitHub.
   <br> If you want to do this, please use this command:

```md  
git clone https://github.com/dima666Sik/telegram-bot-vacancies.git
```
2. To run this project, you will need to install:
    - JDK 21 or higher;
3. Please reload this Maven project to install all dependencies.
4. Also, you will need to insert your own tokens for `telegram.bot.token.access` 
and `openai.token.access` into file **application.properties**.
```properties
telegram.bot.token.access=<your-own-telegram-bot-token-access>
telegram.bot.username=BotVacancies
file.name.vacancies=vacancies.csv

openai.api.url=https://api.openai.com/v1/chat/completions
openai.token.access=<your-own-openai-token-access>
openai.model=gpt-3.5-turbo
openai.temperature=1
openai.max-tokens=256
openai.top-p=1
openai.frequency-penalty=0
openai.presence-penalty=0
```
5. Make sure that you have a file in your code `vacancies.csv` from where vacancies will be read.
6. Feel free to make changes to code that you clone to yourself Git.

## Author Info @DK

- [Linkedin](https://www.linkedin.com)

- [GitHub](https://github.com/dima666Sik)

[Back To The Top](#telegram-bot-with-vacancies)