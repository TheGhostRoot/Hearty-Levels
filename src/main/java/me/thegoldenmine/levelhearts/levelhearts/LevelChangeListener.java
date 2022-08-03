package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelChangeListener implements Listener {
    private final LevelHearts plugin;

    public LevelChangeListener(LevelHearts main) {
        plugin = main;
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        int newLevel = event.getNewLevel();
        int oldLevel = event.getOldLevel();
        Player player = event.getPlayer();
        if (player.hasPermission("heartylevels.play")) {
            // level 1 = 2 hearts = 4 heart points
            if (newLevel > oldLevel) {
                // Leveled up
                double diff = newLevel - oldLevel;
                double newHeathPoints = diff * 2;
                double maxHeath = player.getHealthScale();
                double healthNew = maxHeath + newHeathPoints;
                if (healthNew <= plugin.config.getInt("Max_Hearts")) {
                    player.setHealthScale(healthNew);
                }
            } else {
                // Level down
                double diff = oldLevel - newLevel;
                double newHeathPoints = diff * 2;
                double maxHeath = player.getHealthScale();
                double scale = maxHeath - newHeathPoints;
                if (scale > 1) {
                        player.setHealthScale(scale);
                } else {
                    if (player.getLevel() == 0 || player.getLevel() == 1) {
                        player.setHealth(2);
                    } else {
                        player.setHealth(0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().hasPermission("heartylevels.play")) {
            event.setDroppedExp(event.getDroppedExp() * plugin.config.getInt("Dropped_XP_multiplier"));
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity diffender = event.getEntity();
        if (attacker instanceof Player && diffender instanceof Player) {
            Player playerAtt = (Player) attacker;
            Player playerDef = (Player) diffender;
            int health = (int) playerDef.getHealth();
            int health1 = (int) playerAtt.getHealth();
            if (playerAtt.hasPermission("heartylevels.play") && playerDef.hasPermission("heartylevels.play") && health > 0 && health1 > 0) {
                String messageAtt = ChatColor.GOLD+""+playerDef.getName() +" "+ChatColor.GREEN + "levels: " + String.valueOf(playerDef.getLevel() + "" + ChatColor.RED + " Hearts: " + String.valueOf(health));
                String messageDef = ChatColor.GOLD+""+playerAtt.getName() +" "+ChatColor.GREEN + "levels: " + String.valueOf(playerAtt.getLevel() + "" + ChatColor.RED + " Hearts: " + String.valueOf(health1));
                playerAtt.sendMessage(messageAtt);
                playerDef.sendMessage(messageDef);
            }
        }
    }
}
