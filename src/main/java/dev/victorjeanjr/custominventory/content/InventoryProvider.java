package dev.victorjeanjr.custominventory.content;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class InventoryProvider implements InventoryHolder {

    private Creator creator;
    private InventoryContains inventoryContains;

    public InventoryProvider(Creator creator) {
        this.creator = creator;
        this.inventoryContains = null;
    }

    public boolean isEmpty(Inventory inventory) {
        if(Objects.isNull(this.inventoryContains)) this.setContains(new InventoryContains(inventory));
        return this.inventoryContains.getItems().isEmpty();
    }

    public abstract void init(Player player, InventoryContains inventoryContains);

    public void open(Player player, Consumer<? super Player> action) {
        Inventory inventory = this.getInventory();
        if(this.isEmpty(inventory)) this.init(player, this.getContains());
        player.openInventory(inventory);
        action.accept(player);
    }

    private void setContains(InventoryContains inventoryContains) {
        this.inventoryContains = inventoryContains;
    }

    public InventoryContains getContains() {
        return inventoryContains;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, this.creator.size, this.creator.title);
    }

    public static class Creator {
        private String title;
        private int size;

        private Creator(String title, int size) {
            this.title = title;
            this.size = size;
        }

        public static Creator of(String title, int size) {
            return new Creator(title, size > 6 ? size : size*9);
        }
    }

}

