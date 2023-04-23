package ru.mamakapa.ememeSenderFunctionality.bot.command;

import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;

import java.util.List;
import java.util.function.Function;

public final class CommandHandler<MESSAGE> {
    private final List<BotCommand<MESSAGE>> commands;
    private final Function<MESSAGE, String> getCommandNameFunction;

    public CommandHandler(List<BotCommand<MESSAGE>> commands, Function<MESSAGE, String> commandNameFunction) {
        this.commands = commands;
        this.getCommandNameFunction = commandNameFunction;
    }

    public void handle(MESSAGE message){
        commands.stream()
                .filter(abstractBotCommand -> abstractBotCommand.command.equals(getCommandNameFunction.apply(message)))
                .findFirst()
                .orElseThrow(() -> new NonHandleCommandException(getCommandNameFunction.apply(message)))
                .execute(message);
    }


}
