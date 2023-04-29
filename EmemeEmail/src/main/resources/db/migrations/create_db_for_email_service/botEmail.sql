CREATE TABLE botEmail(
    email_id BIGINT,
    botUser_id BIGINT,
    FOREIGN KEY (email_id) REFERENCES imapEmail (id) ON DELETE CASCADE,
    FOREIGN KEY (botUser_id) REFERENCES botUser (id) ON DELETE CASCADE,
    CONSTRAINT unique_emailId_and_botUserId UNIQUE (email_id, botUser_id)
);