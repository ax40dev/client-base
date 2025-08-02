package dev.twerklife.api.manager.command;

import dev.twerklife.api.utilities.ChatUtils;
import dev.twerklife.api.utilities.IMinecraft;

import java.util.Arrays;
import java.util.List;

public abstract class Command implements IMinecraft {
    private final String name;
    private final String description;
    private final String syntax;
    private final List<String> aliases;

    public Command() {
        RegisterCommand annotation = this.getClass().getAnnotation(RegisterCommand.class);
        if (annotation == null) {
            throw new IllegalStateException("Command " + getClass().getSimpleName() + " must have @RegisterCommand annotation!");
        }

        this.name = annotation.name();
        this.description = annotation.description();
        this.syntax = annotation.syntax();
        this.aliases = Arrays.asList(annotation.aliases());
    }

    public abstract void onCommand(String[] args);

    /**
     * Safely checks if a string can be parsed as an integer.
     */
    protected boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Sends a formatted error message when arguments are invalid.
     */
    protected void sendInvalidArgsError() {
        ChatUtils.sendMessage("§cInvalid arguments! Usage: §7" + this.getSyntax());
    }

    /**
     * Sends a message when the command requires a player (not usable in console).
     */
    protected void sendPlayerOnlyCommandError() {
        ChatUtils.sendMessage("§cThis command can only be used by a player!");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSyntax() {
        return this.syntax;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public void sendSyntax() {
        ChatUtils.sendMessage("§eUsage: §7" + this.getSyntax());
    }
}
