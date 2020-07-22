package dev.victorjeanjr.custominventory.tester;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerTest implements Listener {

    @EventHandler
    public void fakeCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String command = e.getMessage().replace("/", "");
        if(command.equalsIgnoreCase("custominventory")) {
            e.setCancelled(true);
            new Test().open(player, x -> {
                x.playSound(x.getLocation(), Sound.NOTE_PIANO, 0.5f, 0.5f);
                x.sendMessage("Inventario de teste.");
            });
        }
    }

}
