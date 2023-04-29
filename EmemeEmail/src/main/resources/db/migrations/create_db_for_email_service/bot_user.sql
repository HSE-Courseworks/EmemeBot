CREATE TYPE messengerType AS ENUM ('VK', 'TG');

CREATE TABLE botUser(
    id BIGSERIAL PRIMARY KEY,
    chatId BIGINT UNIQUE,
    type messengerType
);