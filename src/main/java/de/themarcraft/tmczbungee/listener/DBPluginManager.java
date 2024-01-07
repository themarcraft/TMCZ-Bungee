package de.themarcraft.tmczbungee.listener;

import de.themarcraft.tmczbungee.Main;
import net.md_5.bungee.api.ChatColor;

import java.sql.*;

public class DBPluginManager {

    Main plugin;
    private Connection connection;

    public DBPluginManager(Main plugin) {
        this.plugin = plugin;
    }


    public Connection getConnection() throws SQLException {

        if (connection != null) {
            return connection;
        }

        //Try to connect to my MySQL database running locally
        String host = "127.0.0.1";
        String database = "tmcz_Network";
        String user = "root";
        String passwd = "marvin1234";

        plugin.beforeLog("Connecting to Database...");
        plugin.beforeLog(ChatColor.YELLOW + "Host: " + ChatColor.AQUA + host);
        plugin.beforeLog(ChatColor.YELLOW + "Database: " + ChatColor.AQUA + database);
        plugin.beforeLog(ChatColor.YELLOW + "User: " + ChatColor.AQUA + user);
        plugin.beforeLog(ChatColor.YELLOW + "Password: " + ChatColor.AQUA + "************");

        Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, passwd);

        this.connection = connection;

        plugin.beforeLog(ChatColor.GREEN + "Connected to database");

        return connection;
    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        //Create the player_stats table
        String createMessagingSystem = "CREATE TABLE IF NOT EXISTS `messaging` (`player` VARCHAR(255),`server` VARCHAR(255), `msg` VARCHAR(255) NUlL) ENGINE = InnoDB;";


        statement.execute(createMessagingSystem);

        statement.close();

    }

    public String getPrefix(){
        try {
            PreparedStatement statement = plugin.database.getConnection().prepareStatement("SELECT * FROM tmczSettings");

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("prefix");

        }catch (SQLException e){
            return null;
        }
    }

    public String getNoPermission(){
        try {
            PreparedStatement statement = plugin.database.getConnection().prepareStatement("SELECT * FROM tmczSettings");

            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("nopermission");

        }catch (SQLException e){
            return null;
        }
    }

    public String getPlayerOnly(){
        try {
            PreparedStatement statement = plugin.database.getConnection().prepareStatement("SELECT * FROM tmczSettings");

            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("playeronly");

        }catch (SQLException e){
            return null;
        }
    }

    public String getInvalidPlayer(){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM tmczSettings");

            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("validplayer");

        }catch (SQLException e){
            return null;
        }
    }
}
