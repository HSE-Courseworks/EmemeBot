CREATE TYPE messengerType AS ENUM ('VK', 'TG');

CREATE TABLE botUser(
    id BIGSERIAL PRIMARY KEY,
    chatId BIGINT,
    type messengerType,
    CONSTRAINT unique_chatId_with_type UNIQUE (chatId, type)
);