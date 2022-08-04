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
            System.out.println("<---{ Creating New Config Files }--->");
            System.out.println(" ");
            if (dataFolder.mkdir()) {
                System.out.println("  <- Folder Created ->");
            } else {
                System.out.println("  <- Couldn't Create The Folder ->");
            }
            System.out.println(" ");
            if (ConfigFile.createNewFile()) {
                System.out.println("  <- Config File Created ->");
            } else {
                System.out.println("  <- Couldn't Create Config File ->");
            }
            setInt("Dropped_XP_multiplier", 2);
            setInt("Max_Hearts", 30);
            setInt("Min_Hearts", 10);
        }
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                System.out.println("  <- Created A Folder ->");
                System.out.println(" ");
            } else {
                System.out.println("  <- Couldn't Create The Folder ->");
            }
        }

        if (!ConfigFile.exists()) {
            if (ConfigFile.createNewFile()) {
                System.out.println("  <- Created config.yml ->");
            } else {
                System.out.println("  <- Couldn't Create Config File ->");
            }
            System.out.println(" ");
            try {
                ConfigData.load(ConfigFile);
                System.out.println("  <- Loaded config.yml ->");
                System.out.println(" ");
            } catch (Exception e) {
                System.out.println("  <- Couldn't Load config.yml ->");
                System.out.println(" ");
                System.out.println("- HeartyLevels ERROR - trying to load config.yml");
            }
        }
    }

    // Int
    public void setInt(String path, int i) {
        ConfigData.set(path, i);
        saveConfig();
        reloadBoss();
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
    public synchronized void reloadBoss() {
        ConfigData = YamlConfiguration.loadConfiguration(ConfigFile);
    }
}
