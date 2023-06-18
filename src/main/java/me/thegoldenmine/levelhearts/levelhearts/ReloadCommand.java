package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private LevelHearts plugin;

    public ReloadCommand(LevelHearts main) {
        plugin = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof CommandSender || sender instanceof Player) {
            plugin.config.reloadConfig();
            plugin.getLogger().info("HeartyLevels | Config Reloaded Successfully");
        }
        return true;
    }
}
