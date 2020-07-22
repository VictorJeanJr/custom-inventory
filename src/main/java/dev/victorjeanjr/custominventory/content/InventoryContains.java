package dev.victorjeanjr.custominventory.content;

import com.google.common.collect.Lists;
import dev.victorjeanjr.custominventory.objects.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Objects;

public class InventoryContains implements Listener {

    private Inventory inventory;
    private Collection<ClickableItem> items;

    private InventoryContains() {
        this.inventory = null;
        this.items = Lists.newCopyOnWriteArrayList();
    }

    public InventoryContains(Inventory inventory) {
        this.inventory = inventory;
        this.items = Lists.newCopyOnWriteArrayList();
    }

    public void fill(int slot, ClickableItem clickableItem) {
        this.inventory.setItem(slot, clickableItem.getItemStack());
        this.items.add(clickableItem);
    }

    public void fill(int x, int y, ClickableItem clickableItem) {
        this.fill((y-1)*9 + (x-1), clickableItem);
    }

    public void fill(ClickableItem clickableItem) {
        this.inventory.addItem(clickableItem.getItemStack());
        this.items.add(clickableItem);
    }

    public void fillBorder(ClickableItem clickableItem) {
        int size = inventory.getSize(), rows = size / 9;

        if(rows < 3) return;

        for (int i = 0; i <= 8; i++) {
            this.fill(i, clickableItem);
        }
        for(int s = 8; s < (this.inventory.getSize() - 9); s += 9) {
            int lastSlot = s + 1;
            this.fill(s, clickableItem);
            this.fill(lastSlot, clickableItem);
        }
        for (int lr = (this.inventory.getSize() - 9); lr < this.inventory.getSize(); lr++) {
            this.fill(lr, clickableItem);
        }
    }

    private void execute(ItemStack itemStack, InventoryClickEvent event) {
        ClickableItem clickableItem = this.items.stream()
                .filter(e -> itemStack.equals(e.getItemStack())).findFirst().orElse(null);
        if(Objects.nonNull(clickableItem)) clickableItem.getConsumer().accept(event);
    }

    public Collection<ClickableItem> getItems() {
        return items;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player)e.getWhoClicked();

        ItemStack itemStack = e.getCurrentItem();

        if(Objects.isNull(itemStack)) return;
        if(itemStack.getType() == Material.AIR) return;

        Inventory inventory = e.getInventory();

        if(!(e.getInventory().getHolder() instanceof InventoryProvider)) return;
        InventoryProvider provider = (InventoryProvider)e.getInventory().getHolder();

        if(provider.isEmpty(inventory)) provider.init(player, provider.getContains());
        provider.getContains().execute(itemStack, e);
    }

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new InventoryContains(), plugin);
    }

}
