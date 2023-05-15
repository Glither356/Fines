package fines.fines;

import commands.*;
import events.playerJoin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

//import java.io.File;

public final class Fines extends JavaPlugin {
    private FileConfiguration _config = getConfig();

    public static FileConfiguration config;

    public static String path = "/home/container/plugins/fines/data.yml";
    //"C:\\Users\\user\\Desktop\\BlackWhite\\plugins\\fines\\data.yml";//"/home/container/plugins/fines/data.yml";

    @Override
    public void onEnable() {
        try {
            _config.load(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        config = _config;

        getCommand("invoice").setExecutor(new invoice());
        getCommand("invoicehelp").setExecutor(new invoiceHELP());
        getCommand("invoicelist").setExecutor(new invoicelist());
        getCommand("invoiceremove").setExecutor(new invoiceremove());
        getCommand("invoiceplayerlist").setExecutor(new invoicePlayerLIST());

        getServer().getPluginManager().registerEvents(new playerJoin(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
