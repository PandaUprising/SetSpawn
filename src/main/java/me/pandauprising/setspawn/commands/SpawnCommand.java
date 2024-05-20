package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.SetSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class SpawnCommand implements CommandExecutor {
    private final Plugin plugin;
    private final HashMap<String, Long> cooldowns = new HashMap<>();

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("no-player"))));
            return true;
        }

        if(!player.hasPermission("setspawn.spawn")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("no-permission"))));
            return true;
        }

        Location location = plugin.getConfig().getLocation("spawn");

        if(location == null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("no-spawnpoint"))));
            return true;
        }

        if(cooldowns.containsKey(player.getName())) {
            long secondsLeft = ((cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000);

            if(secondsLeft > 0) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("cooldown-message")).replace("%Time%", String.valueOf(secondsLeft))));
                return true;
            }
        }

        teleportPlayer(player, location);
        return true;
    }

    private void teleportPlayer(Player player, Location location) {
        int cooldownTime = plugin.getConfig().getInt("cooldown-time");
        int countdownTime = plugin.getConfig().getInt("countdown-time");

        cooldowns.put(player.getName(), System.currentTimeMillis() + (cooldownTime * 1000L));

        new Countdown(player, countdownTime, plugin).start(() -> {
            player.teleport(location);
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));
        });
    }
}

final class Countdown {
    private final Player p;
    private int countdownTine;
    private final Plugin plugin;
    BukkitTask task;

    public Countdown(final Player p, final int countdownTime, Plugin plugin) {
        this.p = p;
        this.countdownTine = countdownTime;
        this.plugin = plugin;
    }

    public void start(final Runnable callback) {
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (countdownTine <= 0) {
                task.cancel();
                callback.run();
                return;
            }

            p.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("countdown-message")).replace("%Time%", String.valueOf(countdownTine)))));
            this.countdownTine--;
        }, 0L, 20L);
    }
}

