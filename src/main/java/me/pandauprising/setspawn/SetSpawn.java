package me.pandauprising.setspawn;

import me.pandauprising.setspawn.commands.SetSpawnCommand;
import me.pandauprising.setspawn.commands.SpawnCommand;
import me.pandauprising.setspawn.listeners.SpawnListener;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SetSpawn extends JavaPlugin {
    ConsoleCommandSender clogger = this.getServer().getConsoleSender();
    @Override
    public void onEnable() {

        clogger.sendMessage(ChatColor.AQUA + "---------------------------------------");
        clogger.sendMessage(ChatColor.GREEN + "     Thank You For Using SetSpawn!");
        clogger.sendMessage(ChatColor.AQUA + "---------------------------------------");

        //config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));

        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
    }

    @Override
    public void onDisable() {

        clogger.sendMessage(ChatColor.DARK_RED + "---------------------------------------");
        clogger.sendMessage(ChatColor.GOLD + "                Goodbye!");
        clogger.sendMessage(ChatColor.DARK_RED + "---------------------------------------");

    }



}
