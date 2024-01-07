package de.themarcraft.tmczbungee.commands;

import de.themarcraft.tmczbungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class lb extends Command {

    Main plugin;

    public lb(Main plugin){
        super("lb", "themarcraft.lobby", "lobby", "l", "tmcz-bungee:lb", "tmcz-bungee:l", "tmcz-bungee:lobby");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(plugin.getPrefix() + "Dieser Befehl kann nur von einem Spieler ausgeführt werden").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().equalsIgnoreCase("lobby")) {
            player.sendMessage(new ComponentBuilder(plugin.getPrefix() + "§cDu bist bereits mit diesem Server verbunden!").color(ChatColor.RED).create());
            return;
        }
        ServerInfo target = ProxyServer.getInstance().getServerInfo("lobby");
        player.connect(target);
    }
}
