package de.themarcraft.tmczbungee;

import de.themarcraft.tmczbungee.commands.cb;
import de.themarcraft.tmczbungee.commands.lb;
import de.themarcraft.tmczbungee.listener.DBPluginManager;
import de.themarcraft.tmczbungee.utils.Messaging;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public final class Main extends Plugin{

    public DBPluginManager database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("\n" +
                ChatColor.GREEN + " _____  __  __   ___  ____   " + ChatColor.BLUE + " __   __   _         __ " + ChatColor.RESET + "\n" +
                ChatColor.GREEN + "|_   _||  \\/  | / __||_  /  " + ChatColor.BLUE + "  \\ \\ / /  / |       /  \\" + ChatColor.RESET + "\n" +
                ChatColor.GREEN + "  | |  | |\\/| || (__  / /   " + ChatColor.BLUE + "   \\   /   | |   _  | () |" + ChatColor.RESET + "\n" +
                ChatColor.GREEN + "  |_|  |_|  |_| \\___|/___|  " + ChatColor.BLUE + "    \\_/    |_|  (_)  \\__/" + ChatColor.RESET + "\n");

        getProxy().getPluginManager().registerCommand(this, new cb(this));
        getProxy().getPluginManager().registerCommand(this, new lb(this));

        database = new DBPluginManager(this);
        try {
            database.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Messaging messaging = new Messaging(this);

        // Ihr wiederkehrender Task-Code hier
        getProxy().getScheduler().schedule(this, messaging::run, 0, 1, TimeUnit.SECONDS);

        try {
            PreparedStatement statement2 = database.getConnection().prepareStatement("DELETE FROM messaging");

            statement2.executeUpdate();

            statement2.close();
        } catch (SQLException e) {

        }

        log("");
        log("Plugin &b" + getDescription().getName() + ChatColor.GOLD +  " " + getDescription().getVersion() + "&r loaded");
        log("");

    }

    @Override
    public void onDisable() {

        try {
            PreparedStatement statement2 = database.getConnection().prepareStatement("DELETE FROM messaging");

            statement2.executeUpdate();

            statement2.close();
        } catch (SQLException e) {

        }
    }

    public void log(String msg){
        getLogger().info(getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void beforeLog(String msg){
        getLogger().info("TMCZ-Bungee > " + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public String getPrefix() {
        return database.getPrefix();
    }


}
