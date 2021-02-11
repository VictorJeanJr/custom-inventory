package dev.victorjeanjr.custominventory;

import dev.victorjeanjr.custominventory.content.InventoryContains;
import org.bukkit.plugin.java.JavaPlugin;

public class StartPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        InventoryContains.register(this);
    }


}
