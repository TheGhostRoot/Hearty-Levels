package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
                           int maxHeartsPoints = plugin.config.getInt("Max_Hearts") * 2;
                           int minHeartsPoints = plugin.config.getInt("Min_Hearts") * 2;
                           double heartsPoints = player.getHealthScale();
                           int levels = player.getLevel();
                           if (levels == 0) {
                               if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                                   player.setHealthScale(minHeartsPoints);
                               }
                           }
                           if (heartsPoints > maxHeartsPoints) {
                               if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                                   player.setHealthScale(maxHeartsPoints);
                               }
                           }
                           int NewLevels = levels * 2;
                           int scale = minHeartsPoints + NewLevels;
                           if (scale != heartsPoints && scale <= maxHeartsPoints) {
                               if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                                   player.setHealthScale(scale);
                               }
                           }
                           if (heartsPoints < minHeartsPoints) {
                               if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                                   player.setHealthScale(minHeartsPoints);
                               }
                           }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
