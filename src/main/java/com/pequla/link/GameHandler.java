package com.pequla.link;

import com.pequla.link.model.*;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.Instant;
import java.util.List;

public class GameHandler implements Listener {

    private final LittleHooks plugin;

    public GameHandler(LittleHooks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoadEvent(ServerLoadEvent event) {
        new Thread(() -> {
            if (event.getType() == ServerLoadEvent.LoadType.STARTUP) {
                plugin.sendMessage("Server loaded.");
            }
        }).start();
    }

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        if (plugin.getServer().getWorlds().get(0).equals(event.getWorld())) {
            plugin.sendMessage("Loading the world.");
        }
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Server server = plugin.getServer();

        plugin.sendMessage(player, PluginUtils.bold(event.getJoinMessage()) +
                " " + (server.getOnlinePlayers().size()) + "/" + server.getMaxPlayers());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Server server = plugin.getServer();

        plugin.sendMessage(player, PluginUtils.bold(event.getQuitMessage()) +
                " " + (server.getOnlinePlayers().size() - 1) + "/" + server.getMaxPlayers());
    }

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        plugin.sendMessage(player, PluginUtils.bold(event.getDeathMessage()));
    }

    @EventHandler
    public void handlePlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        plugin.sendMessage(player, event.getMessage());
    }
}
