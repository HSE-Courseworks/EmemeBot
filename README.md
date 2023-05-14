# EmemeBot
Бот для агрегации писем с электронной почты в популярные мессенджеры. 
На текущий момент реализована работа с мессенджерами
<a href="https://vk.com/ememe_bot" target="_blank">Вконтакте</a> 
и
<a href="https://t.me/EmemeTelegramBot" target="_blank">Telegram</a> 
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
