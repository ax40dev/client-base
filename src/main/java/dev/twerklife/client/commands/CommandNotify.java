package dev.twerklife.client.commands;

import dev.twerklife.essenti4ls;
import dev.twerklife.api.manager.command.Command;
import dev.twerklife.api.manager.command.RegisterCommand;
import dev.twerklife.api.manager.module.Module;
import dev.twerklife.api.utilities.ChatUtils;
import dev.twerklife.client.modules.client.ModuleCommands;
import net.minecraft.util.Formatting;

@RegisterCommand(name="notify", description="Let's you disable or enable module toggle messages.", syntax="notify <module> <value>", aliases={"chatnotify", "togglemsg", "togglemsgs", "togglemessages"})
public class CommandNotify extends Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            boolean found = false;
            for (Module module : essenti4ls.MODULE_MANAGER.getModules()) {
                if (!module.getName().equalsIgnoreCase(args[0])) continue;
                found = true;
                module.setChatNotify(Boolean.parseBoolean(args[1]));
                ChatUtils.sendMessage(ModuleCommands.getSecondColor() + module.getName() + ModuleCommands.getFirstColor() + " now has Toggle Messages " + (module.isChatNotify() ? Formatting.GREEN + "enabled" : Formatting.RED + "disabled") + ModuleCommands.getFirstColor() + ".", "Notify");
            }
            if (!found) {
                ChatUtils.sendMessage("Could not find module.", "Notify");
            }
        } else {
            this.sendSyntax();
        }
    }
}