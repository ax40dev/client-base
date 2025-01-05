package dev.twerklife.client.commands;

import dev.twerklife.WonderWhale;
import dev.twerklife.api.manager.command.Command;
import dev.twerklife.api.manager.command.RegisterCommand;
import dev.twerklife.api.utilities.ChatUtils;

@RegisterCommand(name="config", description="Let's you save or load your configuration without restarting the game.", syntax="config <save|load>", aliases={"configuration", "cfg"})
public class CommandConfig extends Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("load")) {
                WonderWhale.CONFIG_MANAGER.load();
                ChatUtils.sendMessage("Successfully loaded configuration.", "Config");
            } else if (args[0].equalsIgnoreCase("save")) {
                WonderWhale.CONFIG_MANAGER.save();
                ChatUtils.sendMessage("Successfully saved configuration.", "Config");
            } else {
                this.sendSyntax();
            }
        } else {
            this.sendSyntax();
        }
    }
}