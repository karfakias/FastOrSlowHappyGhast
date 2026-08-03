package io.github.karfakias.fasterhappyghast;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastOrSlowHappyGhast implements ModInitializer {
    public static final String MOD_ID = "fasterhappyghast";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final double VANILLA_HAPPY_GHAST_SPEED = 0.05D;

    private static ModConfig config;

    @Override
    public void onInitialize() {
        reloadConfig();
    }

    public static ModConfig getConfig() {
        return config;
    }

    public static ModConfig reloadConfig() {
        config = ModConfig.load();
        LOGGER.info("Loaded Fast or Slow Happy Ghast config: tamed-idle-speed={}, ridden-speed={}",
                config.tamedIdleSpeed(), config.riddenSpeed());
        return config;
    }

    public static ModConfig updateConfig(ModConfig updatedConfig) {
        updatedConfig.save();
        config = updatedConfig;
        LOGGER.info("Updated Fast or Slow Happy Ghast config: tamed-idle-speed={}, ridden-speed={}",
                config.tamedIdleSpeed(), config.riddenSpeed());
        return config;
    }

    public static void updateHappyGhastSpeed(HappyGhast happyGhast) {
        if (happyGhast.level().isClientSide()) {
            return;
        }

        var flyingSpeed = happyGhast.getAttribute(Attributes.FLYING_SPEED);
        if (flyingSpeed == null) {
            return;
        }

        double targetSpeed = VANILLA_HAPPY_GHAST_SPEED;
        if (!happyGhast.getItemBySlot(EquipmentSlot.BODY).isEmpty()) {
            targetSpeed = happyGhast.getPassengers().isEmpty() ? config.tamedIdleSpeed() : config.riddenSpeed();
        }

        if (Double.compare(flyingSpeed.getBaseValue(), targetSpeed) != 0) {
            flyingSpeed.setBaseValue(targetSpeed);
        }
    }
}
