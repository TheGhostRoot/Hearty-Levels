package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Checker {
    private final LevelHearts plugin;

    public Checker(LevelHearts main) {
        plugin = main;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player != null && player.hasPermission("heartylevels.play")) {
                            boolean b = player.getLevel() == 0 || player.getLevel() == 1;
                            if (b && player.getHealthScale() != 2) {
                                player.setHealthScale(2);
                            }
                            if (player.getLevel() > 0 && player.getLevel() != player.getHealthScale() && player.getLevel() <= plugin.config.getInt("Max_Hearts")) {
                                player.setHealthScale(player.getLevel() * 2);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
