package dev.twerklife.asm.mixins;

import dev.twerklife.WonderWhale;
import dev.twerklife.api.utilities.IMinecraft;
import dev.twerklife.client.events.EventKey;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin implements IMinecraft {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void injectOnKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key != -1) {
            if (mc.currentScreen == null && action == 1) {
                WonderWhale.MODULE_MANAGER.getModules().stream().filter(m -> m.getBind() == key).forEach((a) -> a.toggle(true));
            }
            EventKey event = new EventKey(key, action);
            WonderWhale.EVENT_MANAGER.call(event);
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }
}
