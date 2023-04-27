package ru.mamakapa.ememeSenderFunctionality.bot.command;

import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class CommandHandler<MESSAGE> {
    private final List<BotCommand<MESSAGE>> commands;
    private final Function<MESSAGE, String> getCommandNameFunction;

    public CommandHandler(List<BotCommand<MESSAGE>> commands, Function<MESSAGE, String> commandNameFunction) {
        this.commands = commands;
        this.getCommandNameFunction = commandNameFunction;
    }

    public void handle(MESSAGE message) throws NonHandleCommandException {
        commands.stream()
                .filter(messageBotCommand -> getCommandNameFunction.apply(message)
                        .matches(".*"+messageBotCommand.command+".*"))
                .findFirst()
                .orElseThrow(() -> new NonHandleCommandException(getCommandNameFunction.apply(message)))
                .execute(message);
    }

    public Stream<String> getAllCommands(){
        return this.commands.stream()
                .map(messageBotCommand -> messageBotCommand.command);
    }

}
