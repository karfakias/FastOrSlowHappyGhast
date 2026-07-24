package io.github.derec4.fasterhappyghast.mixin;

import io.github.derec4.fasterhappyghast.FasterHappyGhast;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public class HappyGhastEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void fasterhappyghast$updateSpeed(CallbackInfo ci) {
        FasterHappyGhast.updateHappyGhastSpeed((HappyGhast) (Object) this);
    }
}
