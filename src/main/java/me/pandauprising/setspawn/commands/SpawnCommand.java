package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.SetSpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand(SetSpawn plugin) {
        this.plugin = plugin;
    }
    public HashMap<String, Long> cooldowns = new HashMap<>();
    private final Plugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player p) {

            if (p.hasPermission("setspawn.spawn")) {

                int cooldownTime = SetSpawn.config.getInt("cooldown-time");

                if (cooldowns.containsKey(sender.getName())){
                    long secondsLeft = ((cooldowns.get(sender.getName())/1000 + cooldownTime) - System.currentTimeMillis()/1000);
                    if (secondsLeft>0){

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("cooldown-message"))) + secondsLeft + " seconds!");
                        return true;
                    }
                }

                cooldowns.put(sender.getName(), System.currentTimeMillis());

                Location location = plugin.getConfig().getLocation("spawn");

                if (location != null) {

                    p.teleport(location);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));

                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no-spawnpoint"))));
                }

            }else{

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no-permission"))));

            }

        }else{
            sender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");
        }
        return true;
    }
}