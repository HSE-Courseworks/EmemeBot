# EmemeBot
Бот для агрегации писем с электронной почты в популярные мессенджеры. 
На текущий момент реализована работа с мессенджерами
<a href="https://vk.com/ememe_bot" target="_blank">Вконтакте</a> 
и
<a href="https://t.me/EmemeTelegramBot" target="_blank">Telegram</a>
## Презентация проекта
<a href="https://docs.google.com/presentation/d/1I3fnKtJ64J25Oi_S_Ny-8onJeZWncFCXZYRjP1AeLkg" target="_blank">Project Presentation</a>
![изображение](https://github.com/HSE-Courseworks/EmemeBot/assets/62752481/2c653802-47a2-477a-b360-8135798f8f10)
## Архитектура проекта
![Architecture](https://github.com/HSE-Courseworks/EmemeBot/assets/62752481/2d2e4047-62d4-49ae-9ecf-c9051b88de50)

## EmemeEmail
Сервис, отвечающий за обработку и отслеживание обновлений на электронных почтах пользователей. 
В данном сервисе также производится регистрация пользователей и их эллектронных почт.

### Конфигурация

Для корректного запуска сервиса, необходимо сначала настроить конфигурацию в файле `application.properties`: 
```
app.vkBotBaseUrl=${VK_BOT_BASE_URL} //URL для обмена сообщениями с сервисом бота Вконтакте через HTTP 
app.tgBotBaseUrl=${TG_BOT_BASE_URL} //URL для обмена сообщениями с сервисом бота Telegram через HTTP 
app.test.email=${TESTING_EMAIL} //Для запуска тестов, необязательное поле
app.test.host=${TESTING_HOST} //Для запуска тестов, необязательное поле
app.test.password=${TESTING_PASSWORD} //Для запуска тестов, необязательное поле

server.port=8080 

#openApi
springdoc.swagger-ui.path=/swagger-ui

#postgres //Настраивается в соответсвии с данными для подключения к БД
spring.datasource.driver-class-name= org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5443/emailService?stringtype=unspecified
spring.datasource.username=postgres
spring.datasource.password=ememebot

#migrations
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml

#kafka
spring.kafka.bootstrap-servers=${KAFKA_SERVER:localhost:9092} //Сервер, для асинхронного взаимодействия сервисов (Сервер с запущенным Apache Kafka)
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.add.type.headers=false
kafka.isEnabled=true //Если true, то сервисы общаются асинхронно, с помощью Kafka, при false, сервисы общаются через HTTP
kafka.vkBotTopic=VkUpdate
kafka.tgBotTopic=TelegramUpdate

#S3
aws.accessKey=${AWS_ACCESS_KEY} //Ключ доступа к облачному хранилищу S3
aws.secretKey=${AWS_SECRET_KEY} //Секретный ключ
aws.bucketName=${AWS_BUCKET_NAME} //Название бакета, в который будут загружаться файлы с почты
aws.region=${AWS_REGION:ru-central1} //Автоматически настроены на сервера Яндекс.Cloud
aws.endpoint=${AWS_ENDPOINT:storage.yandexcloud.net}
```

После завершения конфигурации можно собрать проект. 

### Взаимодействие с сервисом
Обработка и агрегация писем с почты происходит автоматически. Удаления файлов из облачного хранилища так же происходит регулярно.
Для регистрации пользователей и получения от них почтовых данных, которые будут проверяться ботом, нужно взаимодействовать с сервисом через REST-API.

Список и краткое описание всех методов API:
![7U-e_62vZJk](https://github.com/HSE-Courseworks/EmemeBot/assets/96997917/f51fdb21-8e55-40ca-935c-c737679a2985)

Более подробные описания методов API и DTO, которыми они оперируют, можно получить по пути `{host}/swagger-ui` или
в open-api спецификации в файле `EmemeEmail.yaml`, который можно найти по пути: `/EmemeEmail/src/main/resources/EmemeEmail.yaml`

После регистрации пользователя и получения обновления с зарегистрированных им почт, сервис отправляет информацию об обновлении с помощью Apache Kafka или с помощью Rest-API ботов, в зависимости от параметра `kafka.isEnabled` из `application.properties`.

## EmemeSenderFunctionality
Вспомогательный модуль-библеотека был сконструирован для общей реализации функционала микросервисов типа Sender(VkBot, TelegramBot, ...) отвечающих за пользовательское взаимодействие с чат-ботом в определенной социальной сети. (ВК, Телеграмм, ...)
Абстрактный класс `BotCommand<MESSAGE>` с абстрактным методом `execute(MESSAGE message)` и полем `String command` вместе с конструктором который нужно будет переопределить классу наследнику, сделаны для более удобного добавления команд чат-бота и их выполнения, а класс `CommandHandler<MESSAGE>` реализован с конструктором со списком комманд и функцией определения имени комманды по классу MESSAGE `public CommandHandler(List<BotCommand<MESSAGE>> commands, Function<MESSAGE, String> commandNameFunction)` для добавления комманд и их соответствующей обработки.
![изображение](https://github.com/HSE-Courseworks/EmemeBot/assets/62752481/b51aa671-edf3-4316-aef9-d874216b092f)

## TelegramBot и VkBot
### Конфигурация
Для работоспособности проекта нужно определить некоторые общие обязательные конфигурации, такие как:
Конфигурационные настройки доступа к облачному хранилищу AWS S3
* AWS_ACCESS_KEY
* AWS_SECRET_KEY
* AWS_BUCKET_NAME

Однако у каждого микросервиса есть свои отдельные необходимые настройки доступа:
#### Telegram application.yaml
* BOT_TOKEN - Токен телеграмм бота
```
server:
  port: 9999

service:
  emailUrl: ${EMAIL_URL:http://localhost:8080}

bot:
  telegram:
    token: ${BOT_TOKEN} // Токен телеграмм бота

springdoc:
  swagger-ui:
    path: swagger-ui

kafka:
  serverUrl: ${KAFKA_SERVER_URL:localhost:9092}
  groupId: ${KAFKA_MESSAGE_GROUP_ID:"letter_message"}
  topicName: ${KAFKA_TOPIC_NAME:TelegramUpdate}
  isEnabled: true

http:
  controller:
    isEnabled: true

aws:
  accessKey: ${AWS_ACCESS_KEY}
  secretKey: ${AWS_SECRET_KEY}
  bucketName: ${AWS_BUCKET_NAME}
  region: ${AWS_REGION:ru-central1}
  endpoint: ${AWS_ENDPOINT:storage.yandexcloud.net}
```
#### Vk application.yaml
* token: BOT_TOKEN - Токен ВК бота
* groupId: 220038078 - ID группы сообщества-бота
* confirmationCode: SERVER_RESPONSE - Callback настройка ответа микросервиса на подтверждение webhook адреса
* secret: ${SECRET} // Callback секретный ключ для идентификации получаемых обновлений
```
server:
  port: 9090

springdoc:
  swagger-ui:
    path: "/swagger-ui"

vk:
  bot:
    config:
      token: ${BOT_TOKEN} // Токен ВК бота
      groupId: 220038078 // ID группы сообщества-бота
      callback:
        confirmationCode: ${SERVER_RESPONSE} // Callback настройка ответа микросервиса на подтверждение webhook адреса
        secret: ${SECRET} // Callback секретный ключ для идентификации присылаемых обновлений

kafka:
  serverUrl: ${KAFKA_SERVER_URL:localhost:9092}
  groupId: ${KAFKA_MESSAGE_GROUP_ID:"letter_message"}
  topicName: ${KAFKA_TOPIC_NAME:VkUpdate}
  isEnabled: true

http:
  controller:
    isEnabled: true

service:
  emailUrl: http://localhost:8080/

aws:
  accessKey: ${AWS_ACCESS_KEY}
  secretKey: ${AWS_SECRET_KEY}
  bucketName: ${AWS_BUCKET_NAME}
  region: ${AWS_REGION:ru-central1}
  endpoint: ${AWS_ENDPOINT:storage.yandexcloud.net}
```

[![REST API TELEGRAM TITLE](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=REST+API+TELEGRAM+BOT+MICROSERVICE)](https://git.io/typing-svg)
![Telegram REST API](https://github.com/HSE-Courseworks/EmemeBot/assets/62752481/2be899fa-8d4a-4e53-bb5b-f667b4f67873)

[![REST API VK TITLE](https://readme-typing-svg.herokuapp.com?color=%2336BCF7&lines=REST+API+VK+BOT+MICROSERVICE)](https://git.io/typing-svg)
![VK REST API](https://github.com/HSE-Courseworks/EmemeBot/assets/62752481/eb615248-1332-4ff3-ab38-19126a41b2bc)

