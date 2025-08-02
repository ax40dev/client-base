package dev.twerklife.client.commands;

import dev.twerklife.essenti4ls;
import dev.twerklife.api.manager.command.Command;
import dev.twerklife.api.manager.command.RegisterCommand;
import dev.twerklife.api.manager.module.Module;
import dev.twerklife.api.utilities.ChatUtils;
import dev.twerklife.client.modules.client.ModuleCommands;
import net.minecraft.util.Formatting;

@RegisterCommand(name="drawn", description="Let's you disable or enable module drawing on the module list.", syntax="drawn <module> <value>", aliases={"shown", "show", "draw"})
public class CommandDrawn extends Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            boolean found = false;
            if (args[0].equalsIgnoreCase("all")) {
                for (Module m : essenti4ls.MODULE_MANAGER.getModules()) {
                    m.setDrawn(Boolean.parseBoolean(args[1]));
                }
                ChatUtils.sendMessage(ModuleCommands.getSecondColor() + "All modules" + ModuleCommands.getFirstColor() + " are now " + (Boolean.parseBoolean(args[1]) ? Formatting.GREEN + "shown" : Formatting.RED + "hidden") + ModuleCommands.getFirstColor() + ".");
            } else {
                for (Module module : essenti4ls.MODULE_MANAGER.getModules()) {
                    if (!module.getName().equalsIgnoreCase(args[0])) continue;
                    found = true;
                    module.setDrawn(Boolean.parseBoolean(args[1]));
                    ChatUtils.sendMessage(ModuleCommands.getSecondColor() + module.getName() + ModuleCommands.getFirstColor() + " is now " + (module.isDrawn() ? Formatting.GREEN + "shown" : Formatting.RED + "hidden") + ModuleCommands.getFirstColor() + ".", "Drawn");
                }
            }
            if (!found) {
                ChatUtils.sendMessage("Could not find module.", "Drawn");
            }
        } else {
            this.sendSyntax();
        }
    }
}