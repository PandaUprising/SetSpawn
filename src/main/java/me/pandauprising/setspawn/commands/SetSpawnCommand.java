package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.SetSpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SetSpawnCommand implements CommandExecutor {

    private final SetSpawn plugin;

    public SetSpawnCommand(SetSpawn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player p) {

            if (p.hasPermission("setspawn.setspawn")) {

                Location location = p.getLocation();

                plugin.getConfig().set("spawn", location);

                try {
                    plugin.saveConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-set"))));

            } else {

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no-permission"))));
            }

        } else {

            sender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");
        }

        return true;
    }
}
