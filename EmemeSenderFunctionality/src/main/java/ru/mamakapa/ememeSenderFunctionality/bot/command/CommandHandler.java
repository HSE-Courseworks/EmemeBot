package ru.mamakapa.ememeSenderFunctionality.bot.command;

import ru.mamakapa.ememeSenderFunctionality.bot.command.exception.NonHandleCommandException;

import java.util.List;

public final class CommandHandler {
    private final List<BotCommand> commands;

    public CommandHandler(List<BotCommand> commands) {
        this.commands = commands;
    }

    public void handle(String command){
        commands.stream()
                .filter(abstractBotCommand -> abstractBotCommand.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new NonHandleCommandException(command))
                .execute();
    }


}
