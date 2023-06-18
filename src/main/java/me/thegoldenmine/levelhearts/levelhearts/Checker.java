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
                if (plugin.config.getBool("Override_hearts") && (!Bukkit.getOnlinePlayers().isEmpty())) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player != null && player.hasPermission("heartylevels.play")) {
                                //int levelReq = plugin.config.getInt("Levels_required_for_custom_amount_of_hearts");
                                int amountHearts = plugin.config.getInt("Custom_amount_of_hearts_when_reached_required_level");
                                int maxHearts = plugin.config.getInt("Max_Hearts");
                                int minHearts = plugin.config.getInt("Min_Hearts");
                                int maxHeartsPoints = maxHearts * 2;
                                //int levels = player.getLevel();
                                //int heartsToAdd = Math.max(0, Math.min(levels / levelReq, (maxHearts - minHearts) / amountHearts) * amountHearts); // Calculate the number of hearts to add

                                int newHealthPoints = (minHearts + Math.max(0, Math.min(player.getLevel() / plugin.config.getInt("Levels_required_for_custom_amount_of_hearts"), (plugin.config.getInt("Max_Hearts") - minHearts) / amountHearts) * amountHearts)) * 2;
                                if (newHealthPoints > maxHeartsPoints) {
                                    newHealthPoints = maxHeartsPoints;
                                }

                                if (newHealthPoints != player.getHealthScale() && !player.getGameMode().equals(GameMode.CREATIVE)) {
                                    player.setHealthScale(newHealthPoints);
                                }
                            }
                        }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
