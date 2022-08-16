package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.CooldownManager;
import me.pandauprising.setspawn.SetSpawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SpawnCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();
    private final Plugin plugin;

    public SpawnCommand(SetSpawn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player p) {

            if (p.hasPermission("setspawn.spawn")) {

                long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
                if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN) {

                    Location location = plugin.getConfig().getLocation("spawn");

                    if (location != null) {

                        p.teleport(location);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));

                        cooldownManager.setCooldown(p.getUniqueId(), Math.toIntExact(System.currentTimeMillis()));

                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no-spawnpoint"))));
                    }

                } else {

                    p.sendMessage(ChatColor.RED + "You must wait" + (cooldownManager.DEFAULT_COOLDOWN - TimeUnit.MILLISECONDS.toSeconds(timeLeft)) + " seconds before you can use this command!");

                }


            } else {

                sender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");

            }


        }
        return true;
    }
}