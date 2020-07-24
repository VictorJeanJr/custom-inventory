package dev.victorjeanjr.custominventory;

import dev.victorjeanjr.custominventory.content.InventoryContains;
import dev.victorjeanjr.custominventory.tester.ListenerTest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomInventory extends JavaPlugin {

    @Override
    public void onEnable() {
        InventoryContains.init(this);

        Bukkit.getPluginManager().registerEvents(new ListenerTest(), this);

        System.out.println("Started with success.");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled with success.");
    }
}
