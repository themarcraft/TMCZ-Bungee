package de.themarcraft.tmczbungee.commands;

import de.themarcraft.tmczbungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cb extends Command {
    Main plugin;
    public cb(Main plugin){
        super("cb", "themarcraft.cb01", "cb01", "citybuild", "cb1", "tmcz-bungee:cb", "tmcz-bungee:cb1", "tmcz-bungee:cb01", "tmcz-bungee:cb");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(plugin.getPrefix() + "Dieser Befehl kann nur von einem Spieler ausgeführt werden").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().equalsIgnoreCase("cb01")) {
            player.sendMessage(new ComponentBuilder(plugin.getPrefix() + "§cDu bist bereits mit diesem Server verbunden!").color(ChatColor.RED).create());
            return;
        }
        ServerInfo target = ProxyServer.getInstance().getServerInfo("cb01");
        player.connect(target);
    }
}
