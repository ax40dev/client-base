package dev.twerklife.client.commands;

import dev.twerklife.essenti4ls;
import dev.twerklife.api.manager.command.Command;
import dev.twerklife.api.manager.command.RegisterCommand;
import dev.twerklife.api.manager.module.Module;
import dev.twerklife.api.utilities.ChatUtils;
import dev.twerklife.client.modules.client.ModuleCommands;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@RegisterCommand(
        name = "bind",
        description = "Binds a module to a key.",
        syntax = "bind <module> <key> | clear",
        aliases = {"key", "keybind", "b"}
)
public class CommandBind extends Command {

    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            handleModuleBind(args[0], args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            clearAllBinds();
        } else {
            sendSyntax();
        }
    }

    private void handleModuleBind(String moduleName, String keyInput) {
        Module module = findModuleByName(moduleName);
        if (module == null) {
            ChatUtils.sendMessage("§cModule '" + moduleName + "' not found!", "Bind");
            return;
        }

        try {
            int keyCode = parseKeyInput(keyInput);
            if (keyCode == GLFW.GLFW_KEY_UNKNOWN) {
                ChatUtils.sendMessage("§cInvalid key: '" + keyInput + "'", "Bind");
                return;
            }

            module.setBind(keyCode);
            String keyName = getKeyName(keyCode);
            ChatUtils.sendMessage(
                    "Bound " + ModuleCommands.getSecondColor() + module.getTag() +
                            ModuleCommands.getFirstColor() + " to " + ModuleCommands.getSecondColor() +
                            keyName + ModuleCommands.getFirstColor() + ".",
                    "Bind"
            );
        } catch (Exception e) {
            ChatUtils.sendMessage("§cFailed to bind key: " + e.getMessage(), "Bind");
        }
    }

    private Module findModuleByName(String name) {
        for (Module module : essenti4ls.MODULE_MANAGER.getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    private int parseKeyInput(String input) {
        // Use the protected isInteger() from parent Command class
        if (isInteger(input)) {
            return Integer.parseInt(input);
        }

        // Try parsing as a key name (e.g., "rshift", "a", "f1")
        try {
            return InputUtil.fromTranslationKey("key.keyboard." + input.toLowerCase()).getCode();
        } catch (IllegalArgumentException e) {
            return GLFW.GLFW_KEY_UNKNOWN; // Invalid key
        }
    }

    private String getKeyName(int keyCode) {
        String name = GLFW.glfwGetKeyName(keyCode, GLFW.glfwGetKeyScancode(keyCode));
        return (name != null) ? name.toUpperCase() : "KEY_" + keyCode;
    }

    private void clearAllBinds() {
        essenti4ls.MODULE_MANAGER.getModules().forEach(module -> module.setBind(0));
        ChatUtils.sendMessage("§aAll keybinds cleared.", "Bind");
    }
}
