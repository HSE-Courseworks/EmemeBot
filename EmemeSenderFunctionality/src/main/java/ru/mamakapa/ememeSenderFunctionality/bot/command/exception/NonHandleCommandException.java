package ru.mamakapa.ememeSenderFunctionality.bot.command.exception;

public class NonHandleCommandException extends RuntimeException {
    public NonHandleCommandException(String botCommand){
        super("CommandHandler can not handle '%s' command".formatted(botCommand));
    }
}
