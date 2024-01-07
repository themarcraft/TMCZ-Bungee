package de.themarcraft.tmczbungee.utils;

import de.themarcraft.tmczbungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.PluginMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Messaging {
    Main plugin;

    public Messaging(Main plugin){
        this.plugin = plugin;
    }

    public void run() {
        try {
            Connection connection = plugin.database.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messaging");

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ProxiedPlayer player = plugin.getProxy().getPlayer(resultSet.getString("player"));

                ServerInfo target = ProxyServer.getInstance().getServerInfo(resultSet.getString("server"));
                player.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "Verbinde zu " + target.getName());
                player.connect(target);

                PreparedStatement statement2 = plugin.database.getConnection().prepareStatement("DELETE FROM messaging");

                statement2.executeUpdate();

                statement2.close();
            }


            statement.close();
        }catch (SQLException e){

        }
    }
}
