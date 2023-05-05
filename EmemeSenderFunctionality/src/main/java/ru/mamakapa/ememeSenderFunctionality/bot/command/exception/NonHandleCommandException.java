package ru.mamakapa.ememeSenderFunctionality.bot.command.exception;

public class NonHandleCommandException extends Exception {
    public NonHandleCommandException(String botCommand){
        super("CommandHandler can not handle '%s' command".formatted(botCommand));
    }
}
