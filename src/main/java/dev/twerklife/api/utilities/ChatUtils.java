package dev.twerklife.api.utilities;

import dev.twerklife.client.modules.client.ModuleCommands;
import dev.twerklife.client.modules.client.ModuleCommands.WatermarkModes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtils implements IMinecraft {
    public static void sendMessage(String message) {
        if (mc.player == null || mc.world == null || mc.inGameHud == null) return;
        Text component = Text.literal(getWatermark() + getSeparator() + ModuleCommands.getFirstColor() + message);
        mc.inGameHud.getChatHud().addMessage(component);
    }

    public static void sendMessage(String message, int id) {
        if (mc.player == null || mc.world == null || mc.inGameHud == null) return;
        Text component = Text.literal(getWatermark() + getSeparator() + ModuleCommands.getFirstColor() + message);
        mc.inGameHud.getChatHud().addMessage(component);
    }

    public static void sendMessage(String message, String name) {
        if (mc.player == null || mc.world == null || mc.inGameHud == null) return;
        Text component = Text.literal(getWatermark() + getSeparator() +
                Formatting.AQUA.toString() + "[" + name + "]: " +
                ModuleCommands.getFirstColor() + message);
        mc.inGameHud.getChatHud().addMessage(component);
    }

    public static void sendMessage(String message, String name, int id) {
        if (mc.player == null || mc.world == null || mc.inGameHud == null) return;
        Text component = Text.literal(getWatermark() + getSeparator() +
                Formatting.AQUA.toString() + "[" + name + "]: " +
                ModuleCommands.getFirstColor() + message);
        mc.inGameHud.getChatHud().addMessage(component);
    }

    public static void sendRawMessage(String message) {
        if (mc.player == null || mc.world == null) return;
        mc.player.sendMessage(Text.literal(message));
    }

    private static String getSeparator() {
        WatermarkModes mode = (WatermarkModes) ModuleCommands.INSTANCE.watermarkMode.getValue();
        return mode != WatermarkModes.None ? " " : "";
    }

    public static String getWatermark() {
        WatermarkModes mode = (WatermarkModes) ModuleCommands.INSTANCE.watermarkMode.getValue();
        if (mode == WatermarkModes.None) {
            return "";
        }

        String watermarkText;
        switch (mode) {
            case Custom:
                watermarkText = ModuleCommands.INSTANCE.watermarkText.getValue();
                break;
            case Japanese:
                watermarkText = "⌀";
                break;
            default:
                watermarkText = "⌀";
                break;
        }

        return ModuleCommands.getSecondWatermarkColor().toString() +
                ModuleCommands.getFirstWatermarkColor().toString() +
                watermarkText +
                ModuleCommands.getSecondWatermarkColor().toString();
    }
}
