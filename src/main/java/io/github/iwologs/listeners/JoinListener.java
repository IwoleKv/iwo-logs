package io.github.iwologs.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.getPlayer().sendMessage("Siemuwa, " + player.getName() + "!");
    }
}
