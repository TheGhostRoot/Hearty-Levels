package me.thegoldenmine.levelhearts.levelhearts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class LevelHearts extends JavaPlugin {
    public LevelHearts levelHearts;
    public Checker checker;
    public Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String version = "N/A";
        String name = "HeartyLevels";
        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            getLogger().severe("Failed to setup "+name);
            getLogger().severe("Your server version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!version.equals("N/A")) {
            getLogger().info("Your server is running version " + version);
            switch (version) {
                case "v1_18_R1":
                case "v1_18_R2":
                case "v1_19_R1":
                case "v1_16_R3":
                case "v1_16_R2":
                case "v1_16_R1":
                case "v1_15_R1":
                case "v1_14_R1":
                case "v1_17_R1":
                case "v1_8_R3":
                case "v1_9_R1":
                case "v1_9_R2":
                case "v1_10_R1":
                case "v1_11_R1":
                case "v1_12_R1":
                case "v1_13_R1":
                case "v1_13_R2":
                case "v1_19_R2":
                case "v1_19_R3":
                case "v1_20_R1":
                    levelHearts = this;
                    break;
            }
        }
        // This will return true if the server version was compatible with one of our NMS classes
        // because if it is, our actionbar would not be null
        if (levelHearts != null) {
            try {
                config = new Config(this);
            }catch (IOException e) {
                getLogger().info("-- Couldn't load the config file.");
            }
            checker = new Checker(this);
            getCommand("heartylevels").setExecutor(new ReloadCommand(this));
            getServer().getPluginManager().registerEvents(new LevelChangeListener(this), this);
            getLogger().info("  <> Registered Listener <>");
        } else {
            getLogger().severe("Failed to setup "+name);
            getLogger().severe("Your server version is not compatible with this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
