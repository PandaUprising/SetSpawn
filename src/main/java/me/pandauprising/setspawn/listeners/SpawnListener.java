package me.pandauprising.setspawn.listeners;

import me.pandauprising.setspawn.SetSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class SpawnListener implements Listener {
    private final SetSpawn plugin;

    public SpawnListener(SetSpawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        if(player.hasPlayedBefore()) return;

        Location location = plugin.getConfig().getLocation("spawn");

        if(location == null) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("no-spawnpoint"))));
            return;
        }

        player.teleport(location);
        player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));
    }
}
