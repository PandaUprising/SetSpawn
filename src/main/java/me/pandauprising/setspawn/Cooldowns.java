package me.pandauprising.setspawn;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldowns{

    public static HashMap<UUID, Double> cooldowns;

    public static void setupCooldown(){
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(Player p, int seconds){
        double delay = System.currentTimeMillis() + (seconds* 1000);
        cooldowns.put(p.getUniqueId(), delay);
    }

    public static int getCooldown(Player p){
        return Math.toIntExact(Math.round((cooldowns.get(p.getUniqueId()) - System.currentTimeMillis()/1000)));
    }

    public static boolean checkCooldown(Player p){
        if (!cooldowns.containsKey(p.getUniqueId()) || cooldowns.get(p.getUniqueId()) <= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

}
