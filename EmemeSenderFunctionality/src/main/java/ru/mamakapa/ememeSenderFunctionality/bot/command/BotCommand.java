package ru.mamakapa.ememeSenderFunctionality.bot.command;

public abstract class BotCommand {
    protected final String command;

    protected BotCommand(String command) {
        this.command = command;
    }

    public abstract void execute();
}
