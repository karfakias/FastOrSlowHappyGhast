package io.github.karfakias.fasterhappyghast;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record ModConfig(double tamedIdleSpeed, double riddenSpeed) {
    private static final double DEFAULT_TAMED_IDLE_SPEED = 0.05D;
    private static final double DEFAULT_RIDDEN_SPEED = 0.17D;

    public static final String TAMED_IDLE_SPEED_KEY = "tamed-idle-speed";
    public static final String RIDDEN_SPEED_KEY = "ridden-speed";

    public static ModConfig load() {
        Path configPath = getConfigPath();
        Properties properties = new Properties();

        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                properties.load(reader);
            } catch (IOException exception) {
                FastOrSlowHappyGhast.LOGGER.warn("Could not read {}, using defaults.", configPath, exception);
            }
        }

        ModConfig config = new ModConfig(
                readDouble(properties, TAMED_IDLE_SPEED_KEY, DEFAULT_TAMED_IDLE_SPEED),
                readDouble(properties, RIDDEN_SPEED_KEY, DEFAULT_RIDDEN_SPEED)
        );

        config.save();

        return config;
    }

    public ModConfig withTamedIdleSpeed(double speed) {
        return new ModConfig(speed, riddenSpeed);
    }

    public ModConfig withRiddenSpeed(double speed) {
        return new ModConfig(tamedIdleSpeed, speed);
    }

    public void save() {
        Path configPath = getConfigPath();
        Properties properties = new Properties();
        properties.setProperty(TAMED_IDLE_SPEED_KEY, Double.toString(tamedIdleSpeed));
        properties.setProperty(RIDDEN_SPEED_KEY, Double.toString(riddenSpeed));

        try {
            Files.createDirectories(configPath.getParent());
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                properties.store(writer, "Fast or Slow Happy Ghast config. Untamed Happy Ghasts keep the vanilla speed.");
            }
        } catch (IOException exception) {
            FastOrSlowHappyGhast.LOGGER.warn("Could not write {}.", configPath, exception);
        }
    }

    private static double readDouble(Properties properties, String key, double fallback) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return fallback;
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException exception) {
            FastOrSlowHappyGhast.LOGGER.warn("Invalid value '{}' for '{}', using {}.", value, key, fallback);
            return fallback;
        }
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("fasterhappyghast.properties");
    }
}
