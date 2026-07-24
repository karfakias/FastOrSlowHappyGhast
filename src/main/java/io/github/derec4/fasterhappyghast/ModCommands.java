package io.github.derec4.fasterhappyghast;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.math.BigDecimal;

public final class ModCommands {
    private ModCommands() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("happyghast")
                        .requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
                        .executes(context -> sendHelp(context.getSource()))
                        .then(Commands.literal("help")
                                .executes(context -> sendHelp(context.getSource())))
                        .then(Commands.literal("get")
                                .executes(context -> sendConfig(context.getSource())))
                        .then(Commands.literal("reload")
                                .executes(context -> reloadConfig(context.getSource())))
                        .then(Commands.literal("set")
                                .then(Commands.literal(ModConfig.TAMED_IDLE_SPEED_KEY)
                                        .then(Commands.argument("speed", DoubleArgumentType.doubleArg(0.0D))
                                                .executes(context -> setTamedIdleSpeed(
                                                        context.getSource(),
                                                        DoubleArgumentType.getDouble(context, "speed")
                                                ))))
                                .then(Commands.literal(ModConfig.RIDDEN_SPEED_KEY)
                                        .then(Commands.argument("speed", DoubleArgumentType.doubleArg(0.0D))
                                                .executes(context -> setRiddenSpeed(
                                                        context.getSource(),
                                                        DoubleArgumentType.getDouble(context, "speed")
                                                ))))));
    }

    private static int sendHelp(CommandSourceStack source) {
        ModConfig config = FasterHappyGhast.getConfig();
        source.sendSuccess(() -> header()
                .append(Component.literal("\nCurrent speeds").withStyle(ChatFormatting.GRAY))
                .append(speedLine(ModConfig.TAMED_IDLE_SPEED_KEY, config.tamedIdleSpeed(), "harnessed, no passengers"))
                .append(speedLine(ModConfig.RIDDEN_SPEED_KEY, config.riddenSpeed(), "harnessed with passengers"))
                .append(Component.literal("\nCommands").withStyle(ChatFormatting.GRAY))
                .append(commandLine("/happyghast get", "show current values"))
                .append(commandLine("/happyghast reload", "reload from config"))
                .append(commandLine("/happyghast set " + ModConfig.TAMED_IDLE_SPEED_KEY + " <speed>", "save idle speed"))
                .append(commandLine("/happyghast set " + ModConfig.RIDDEN_SPEED_KEY + " <speed>", "save ridden speed")), false);
        return 1;
    }

    private static int sendConfig(CommandSourceStack source) {
        ModConfig config = FasterHappyGhast.getConfig();
        source.sendSuccess(() -> header()
                .append(speedLine(ModConfig.TAMED_IDLE_SPEED_KEY, config.tamedIdleSpeed(), "harnessed, no passengers"))
                .append(speedLine(ModConfig.RIDDEN_SPEED_KEY, config.riddenSpeed(), "harnessed with passengers")), false);
        return 1;
    }

    private static int reloadConfig(CommandSourceStack source) {
        ModConfig config = FasterHappyGhast.reloadConfig();
        source.sendSuccess(() -> prefix()
                .append(Component.literal("Config reloaded. ").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(ModConfig.TAMED_IDLE_SPEED_KEY + "=" + formatSpeed(config.tamedIdleSpeed()))
                        .withStyle(ChatFormatting.AQUA))
                .append(Component.literal(", ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(ModConfig.RIDDEN_SPEED_KEY + "=" + formatSpeed(config.riddenSpeed()))
                        .withStyle(ChatFormatting.AQUA)), true);
        return 1;
    }

    private static int setTamedIdleSpeed(CommandSourceStack source, double speed) {
        ModConfig config = FasterHappyGhast.updateConfig(FasterHappyGhast.getConfig().withTamedIdleSpeed(speed));
        source.sendSuccess(() -> setMessage(ModConfig.TAMED_IDLE_SPEED_KEY, config.tamedIdleSpeed()), true);
        return 1;
    }

    private static int setRiddenSpeed(CommandSourceStack source, double speed) {
        ModConfig config = FasterHappyGhast.updateConfig(FasterHappyGhast.getConfig().withRiddenSpeed(speed));
        source.sendSuccess(() -> setMessage(ModConfig.RIDDEN_SPEED_KEY, config.riddenSpeed()), true);
        return 1;
    }

    private static MutableComponent header() {
        return prefix().append(Component.literal("Happy Ghast speed controls").withStyle(ChatFormatting.YELLOW));
    }

    private static MutableComponent prefix() {
        return Component.literal("[FasterHappyGhast] ").withStyle(ChatFormatting.GOLD);
    }

    private static MutableComponent speedLine(String key, double speed, String description) {
        return Component.literal("\n- ").withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.literal(key).withStyle(ChatFormatting.AQUA))
                .append(Component.literal(" = ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(formatSpeed(speed)).withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" (" + description + ")").withStyle(ChatFormatting.DARK_GRAY));
    }

    private static MutableComponent commandLine(String command, String description) {
        return Component.literal("\n- ").withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.literal(command).withStyle(ChatFormatting.AQUA))
                .append(Component.literal(" - " + description).withStyle(ChatFormatting.GRAY));
    }

    private static MutableComponent setMessage(String key, double speed) {
        return prefix()
                .append(Component.literal("Saved ").withStyle(ChatFormatting.GREEN))
                .append(Component.literal(key).withStyle(ChatFormatting.AQUA))
                .append(Component.literal(" = ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(formatSpeed(speed)).withStyle(ChatFormatting.GREEN));
    }

    private static String formatSpeed(double speed) {
        return BigDecimal.valueOf(speed).stripTrailingZeros().toPlainString();
    }
}
