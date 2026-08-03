package io.github.karfakias.fasterhappyghast.mixin;

import io.github.karfakias.fasterhappyghast.FastOrSlowHappyGhast;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public class HappyGhastEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void fasterhappyghast$updateSpeed(CallbackInfo ci) {
        FastOrSlowHappyGhast.updateHappyGhastSpeed((HappyGhast) (Object) this);
    }
}
