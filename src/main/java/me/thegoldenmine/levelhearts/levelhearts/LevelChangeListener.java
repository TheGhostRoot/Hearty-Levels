package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelChangeListener implements Listener {
    private final LevelHearts plugin;

    public LevelChangeListener(LevelHearts main) {
        plugin = main;
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("heartylevels.play")) {
            int levelDifference = (event.getNewLevel() - event.getOldLevel());
            int levelsPerHeartIncrease = plugin.config.getInt("Levels_required_for_custom_amount_of_hearts");
            int heartsPerLevelIncrease = plugin.config.getInt("Custom_amount_of_hearts_when_reached_required_level");;
            double currentHealthPoints = player.getHealthScale();
            double newHealthPoints = currentHealthPoints;

            if (levelDifference > 0) {
                // Player gained levels
                int heartsToAdd = (int) Math.floor((double) levelDifference / levelsPerHeartIncrease) * heartsPerLevelIncrease;
                newHealthPoints = Math.min(currentHealthPoints + heartsToAdd, (double) plugin.config.getInt("Max_Hearts") * 2);
            } else if (levelDifference < 0) {
                // Player lost levels
                int heartsToRemove = (int) Math.floor((double) Math.abs(levelDifference) / levelsPerHeartIncrease) * heartsPerLevelIncrease;
                newHealthPoints = Math.max(currentHealthPoints - heartsToRemove, (double) plugin.config.getInt("Min_Hearts") * 2);
            }

            if (!player.getGameMode().equals(GameMode.CREATIVE) && newHealthPoints != currentHealthPoints) {
                player.setHealthScale(newHealthPoints);
                player.setMaxHealth(newHealthPoints);
            }

        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().hasPermission("heartylevels.play")) {
            event.setDroppedExp(event.getDroppedExp() * plugin.config.getInt("Dropped_XP_multiplier"));
        }
    }
}
