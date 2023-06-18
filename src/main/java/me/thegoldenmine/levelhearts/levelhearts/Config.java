package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
;
import java.io.File;
import java.io.IOException;

public class Config {
    private final LevelHearts plugin;
    private final File ConfigFile;
    private FileConfiguration ConfigData;

    public Config(LevelHearts main) throws IOException {
        plugin = main;
        File dataFolder = plugin.getDataFolder();
        ConfigFile = new File(dataFolder, "config.yml");
        ConfigData = YamlConfiguration.loadConfiguration(ConfigFile);
        if (!dataFolder.exists() && !ConfigFile.exists()) {
            plugin.getLogger().info("<---{ Creating New Config Files }--->");
            plugin.getLogger().info(" ");
            if (dataFolder.mkdir()) {
                plugin.getLogger().info("  <- Folder Created ->");
            } else {
                plugin.getLogger().info("  <- Couldn't Create The Folder ->");
            }
            plugin.getLogger().info(" ");
            if (ConfigFile.createNewFile()) {
                plugin.getLogger().info("  <- Config File Created ->");
            } else {
                plugin.getLogger().info("  <- Couldn't Create Config File ->");
            }
            setInt("Dropped_XP_multiplier", 2);
            setInt("Max_Hearts", 30);
            setInt("Min_Hearts", 10);
            setInt("Levels_required_for_custom_amount_of_hearts", 1);
            setInt("Custom_amount_of_hearts_when_reached_required_level", 1);
            setBool("Override_hearts", true);
            //setBool("Disable_on_hit_message", false);
            //setStr("On_hit_message", "§2§oLevels:§2§l <level> §7§l| §c§oHearts:§2§l <heart>");
        }
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                plugin.getLogger().info("  <- Created A Folder ->");
                plugin.getLogger().info(" ");
            } else {
                plugin.getLogger().info("  <- Couldn't Create The Folder ->");
            }
        }

        if (!ConfigFile.exists()) {
            if (ConfigFile.createNewFile()) {
                plugin.getLogger().info("  <- Created config.yml ->");
            } else {
                plugin.getLogger().info("  <- Couldn't Create Config File ->");
            }
            plugin.getLogger().info(" ");
            try {
                ConfigData.load(ConfigFile);
                plugin.getLogger().info("  <- Loaded config.yml ->");
                plugin.getLogger().info(" ");
            } catch (Exception e) {
                plugin.getLogger().info("  <- Couldn't Load config.yml ->");
                plugin.getLogger().info(" ");
                plugin.getLogger().info("- HeartyLevels ERROR - trying to load config.yml");
            }
        }
    }

    // string

    public void setStr(String path, String i) {
        ConfigData.set(path, i);
        saveConfig();
        reloadConfig();
    }

    public String getStr(String path) {
        return ConfigData.getString(path);
    }

    // bool

    public void setBool(String path, boolean i) {
        ConfigData.set(path, i);
        saveConfig();
        reloadConfig();
    }

    public boolean getBool(String path) {
        return ConfigData.getBoolean(path);
    }

    // Int
    public void setInt(String path, int i) {
        ConfigData.set(path, i);
        saveConfig();
        reloadConfig();
    }

    public int getInt(String path) {
        return ConfigData.getInt(path);
    }

    // save
    public synchronized void saveConfig() {
        try {
            ConfigData.save(ConfigFile);
        } catch (IOException e) {
            throw new RuntimeException("- HeartyLevels ERROR - Cannot save config.yml", e);
        }
    }

    //reload
    public synchronized void reloadConfig() {
        ConfigData = YamlConfiguration.loadConfiguration(ConfigFile);
    }
}
