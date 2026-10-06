package io.github.karfakias.fasterhappyghast;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ModConfigTest {
    @TempDir
    Path directory;

    @Test
    void createsDefaultConfigOnFirstLaunch() {
        Path path = directory.resolve("config/fasterhappyghast.properties");
        assertEquals(new ModConfig(0.05, 0.17), ModConfig.load(path));
        assertTrue(Files.isRegularFile(path));
    }

    @Test
    void preservesExistingSettingsAndSavesUpdates() throws IOException {
        Path path = directory.resolve("fasterhappyghast.properties");
        Files.writeString(path, "tamed-idle-speed=0.08\nridden-speed=0.3\n");
        ModConfig config = ModConfig.load(path);
        assertEquals(new ModConfig(0.08, 0.3), config);
        config.withTamedIdleSpeed(0).withRiddenSpeed(0.4).save(path);
        assertEquals(new ModConfig(0, 0.4), ModConfig.load(path));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NaN", "Infinity", "-Infinity", "-0.1", "1e309", "oops", ""})
    void invalidSettingUsesDefaultWithoutLosingOtherSetting(String value) throws IOException {
        Path path = directory.resolve("fasterhappyghast.properties");
        Files.writeString(path, "tamed-idle-speed=" + value + "\nridden-speed=0.3\n");
        assertEquals(new ModConfig(0.05, 0.3), ModConfig.load(path));
        Files.writeString(path, "tamed-idle-speed=0.08\nridden-speed=" + value + "\n");
        assertEquals(new ModConfig(0.08, 0.17), ModConfig.load(path));
    }

    @Test
    void malformedPropertiesDoNotCrashStartup() throws IOException {
        Path path = directory.resolve("fasterhappyghast.properties");
        Files.writeString(path, "tamed-idle-speed=\\uZZZZ\n");
        assertEquals(new ModConfig(0.05, 0.17), ModConfig.load(path));
    }

    @Test
    void rejectsInvalidProgrammaticUpdates() {
        ModConfig config = new ModConfig(0.05, 0.17);
        for (double value : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, -1}) {
            assertThrows(IllegalArgumentException.class, () -> config.withTamedIdleSpeed(value));
            assertThrows(IllegalArgumentException.class, () -> config.withRiddenSpeed(value));
        }
    }
}
