package dev.twerklife.api.manager.command;

import dev.twerklife.essenti4ls;
import dev.twerklife.api.manager.event.EventListener;
import dev.twerklife.api.utilities.ChatUtils;
import dev.twerklife.api.utilities.IMinecraft;
import dev.twerklife.client.commands.*;
import dev.twerklife.client.events.EventChatSend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements IMinecraft, EventListener {
    private String prefix = ".";
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        essenti4ls.EVENT_MANAGER.register(this);
        registerDefaultCommands();
    }

    private void registerDefaultCommands() {
        register(new CommandBind());
        register(new CommandConfig());
        register(new CommandDrawn());
        register(new CommandFolder());
        register(new CommandFriend());
        register(new CommandNotify());
        register(new CommandPrefix());
        register(new CommandTag());
        register(new CommandToggle());
    }

    public void register(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Cannot register null command!");
        }
        commands.add(command);
    }

    @Override
    public void onChatSend(EventChatSend event) {
        String message = event.getMessage().trim();

        if (message.equals(prefix)) {
            event.cancel();
            showCommandList();
            return;
        }

        if (message.startsWith(prefix)) {
            event.cancel();
            executeCommand(message.substring(prefix.length()).trim());
        }
    }

    private void showCommandList() {
        // Header
        ChatUtils.sendMessage("§6§l─── Available Commands ───");

        // Command list
        for (Command cmd : commands) {
            String aliases = cmd.getAliases().isEmpty() ? "" :
                    " (§e" + String.join("§7, §e", cmd.getAliases()) + "§6)";

            ChatUtils.sendMessage(
                    " §7» §b" + cmd.getName() + "§7 - " + cmd.getDescription() + aliases +
                            "\n   §8Usage: §e" + cmd.getSyntax()
            );
        }

        // Footer
        ChatUtils.sendMessage("§6§l──────────────────────────");
    }

    private void executeCommand(String input) {
        if (input.isEmpty()) {
            showCommandList();
            return;
        }

        String[] args = input.split("\\s+");
        String commandName = args[0].toLowerCase();

        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(commandName) ||
                    cmd.getAliases().contains(commandName)) {

                mc.inGameHud.getChatHud().addToMessageHistory(prefix + input);
                cmd.onCommand(args.length > 1 ?
                        Arrays.copyOfRange(args, 1, args.length) : new String[0]);
                return;
            }
        }

        ChatUtils.sendMessage("§cUnknown command. Type §e" + prefix + "§c for help.");
    }

    public List<Command> getCommands() {
        return new ArrayList<>(commands);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
