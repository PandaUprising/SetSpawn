package me.pandauprising.setspawn;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown {

        public static boolean triggered = false;
    private static Plugin SetSpawn;

    public static void triggerCooldown() {

            new BukkitRunnable(){

                @Override
                public void run(){

                    triggered = true;

                }
            }.runTaskLater(SetSpawn, 200/*Delay in ticks(1sec = 20ticks)*/);

        }

        public static boolean getTriggered(){
            return triggered;
        }
    }
