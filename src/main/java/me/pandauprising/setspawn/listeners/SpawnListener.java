package me.pandauprising.setspawn.listeners;

import me.pandauprising.setspawn.SetSpawn;
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

        Player p = e.getPlayer();

        if (!e.getPlayer().hasPlayedBefore()){


            Location location = plugin.getConfig().getLocation("spawn");

            if (location != null){

                p.teleport(location);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("spawn-arrival"))));

            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("no-spawnpoint"))));
            }
        }

    }
}
