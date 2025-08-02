package dev.twerklife.asm.mixins;

import dev.twerklife.essenti4ls;
import dev.twerklife.client.events.EventRender2D;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        EventRender2D event = new EventRender2D(tickCounter.getTickDelta(true), context);
        essenti4ls.EVENT_MANAGER.call(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshair(CallbackInfo ci) {
        if (!essenti4ls.MODULE_MANAGER.isModuleEnabled("Crosshair"))
            return;

        ci.cancel();
    }
}
