package me.pandauprising.setspawn.commands;

import me.pandauprising.setspawn.SetSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        if(!(sender instanceof Player player)) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("no-player"))));
            return true;
        }

        if(!player.hasPermission("setspawn.setspawn")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize( Objects.requireNonNull(plugin.getConfig().getString("no-permission"))));
            return true;
        }

        // Validate location before setting
        Location location = player.getLocation();
        if (location.getWorld() == null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Error: Cannot set spawn in an invalid world"));
            return true;
        }

        plugin.getConfig().set("spawn", location);
        plugin.saveConfig();

        player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("spawn-set"))));
        return true;
    }
}
