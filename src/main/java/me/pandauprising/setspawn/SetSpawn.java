package me.pandauprising.setspawn;

import me.pandauprising.setspawn.commands.SetSpawnCommand;
import me.pandauprising.setspawn.commands.SpawnCommand;
import me.pandauprising.setspawn.listeners.SpawnListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class SetSpawn extends JavaPlugin {
    private final ConsoleCommandSender consoleLogger = getServer().getConsoleSender();

    @Override
    public void onEnable() {
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize(""));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<blue>---------------------------------------"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<green>     Thank You For Using SetSpawn!"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<blue>---------------------------------------"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize(""));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<gold>Thank you for supporting me via Modrinth!"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize(""));

        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
            reloadConfig();
        }

        saveConfig();

        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));

        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
    }

    @Override
    public void onDisable() {
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize(""));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<red>---------------------------------------"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<gold>                Goodbye!"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize("<red>---------------------------------------"));
        consoleLogger.sendMessage(MiniMessage.miniMessage().deserialize(""));
    }
}
