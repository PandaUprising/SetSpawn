package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.SetSpawn;
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
    public static final class Countdown {
        private final Player p;
        private int countdownTine;
        private BukkitTask task;

        public Countdown(final Player p, final int countdownTime) {
            this.p = p;
            this.countdownTine = countdownTime;
        }

        public void start(final Runnable callback) {
            this.task = Bukkit.getScheduler().runTaskTimer(SetSpawn.getInstance(), () -> {
                if (countdownTine <= 0) {
                    this.task.cancel();
                    callback.run();
                    return;
                }

                p.sendMessage(String.valueOf(this.countdownTine));
                --this.countdownTine;
            }, 0L, 20L);
        }
    }

    private final Plugin plugin;
    private final HashMap<String, Long> cooldowns = new HashMap<>();

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("setspawn.spawn")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        Location location = plugin.getConfig().getLocation("spawn");

        if (location == null) {
            player.sendMessage(ChatColor.RED + "The spawn point has not been set!");
            return true;
        }

        int cooldownTime = plugin.getConfig().getInt("cooldown-time");

        if (cooldowns.containsKey(player.getName())) {
            long secondsLeft = ((cooldowns.get(player.getName()) / 1000 + cooldownTime) - System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                player.sendMessage(ChatColor.RED + "You must wait " + secondsLeft + " seconds before using this command again!");
                return true;
            }
        }

        cooldowns.put(player.getName(), System.currentTimeMillis());

        int countdownTime = plugin.getConfig().getInt("countdown-time");

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-countdown"))));

        new Countdown(player, countdownTime).start(() -> {
            player.teleport(location);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));
        });

        return true;
    }
}