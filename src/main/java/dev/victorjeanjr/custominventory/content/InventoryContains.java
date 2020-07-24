package dev.victorjeanjr.custominventory.content;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.victorjeanjr.custominventory.objects.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class InventoryContains implements Listener {

    private Inventory inventory;
    private Map<Integer, ClickableItem> items;

    private InventoryContains() {
        this.inventory = null;
        this.items = Maps.newHashMap();
    }

    public InventoryContains(Inventory inventory) {
        this.inventory = inventory;
        this.items = Maps.newHashMap();
    }

    public void fill(int slot, ClickableItem clickableItem) {
        this.inventory.setItem(slot, clickableItem.getItemStack());
        this.items.put(slot, clickableItem);
    }

    public void fill(int x, int y, ClickableItem clickableItem) {
        this.fill((y-1)*9 + (x-1), clickableItem);
    }

    @Warning(reason = "Use with forEach and when false from a break.")
    public boolean fillForEach(int i, ClickableItem clickableItem) {
        int size = this.inventory.getSize(), rows = size / 9;

        if(rows < 2) return false;
        int slot = -1;

        if(i >= 0 && i <= 6) {
            if(size > 2*9) slot = 10 + i;
        } else if(i >= 7 && i <= 13) {
            if(size > 3*9) slot = 19 + (i - 7);
        } else if(i >= 14 && i <= 20) {
            if(size > 4*9) slot = 28 + (i - 14);
        } else if(i >= 21 && i <= 27) {
            if(size > 5*9) slot = 37 + (i - 21);
        }
        if(slot == -1) return false;
        this.fill(slot, clickableItem); return true;
    }

    @Warning(reason = "Use with forEach and when false from a break.")
    public boolean fillForEach(int i, int y, ClickableItem clickableItem) {
        return this.fillForEach((y-1)*7 + i, clickableItem);
    }

    public void fillBorder(ClickableItem clickableItem) {
        int size = this.inventory.getSize(), rows = size / 9;

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

    private void execute(int slot, InventoryClickEvent event) {
        Map.Entry<Integer, ClickableItem> clickableItem = this.items.entrySet().stream()
                .filter(e -> e.getKey() == slot).findFirst().orElse(null);
        if(Objects.nonNull(clickableItem)) clickableItem.getValue().getConsumer().accept(event);
    }

    public Map<Integer, ClickableItem> getItems() {
        return items;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) return;
        ///Player player = (Player)e.getWhoClicked();

        ItemStack itemStack = e.getCurrentItem();

        if(Objects.isNull(itemStack)) return;
        if(itemStack.getType() == Material.AIR) return;

        ///Inventory inventory = e.getInventory();

        if(!(e.getInventory().getHolder() instanceof InventoryProvider)) return;
        InventoryProvider provider = (InventoryProvider)e.getInventory().getHolder();

        ///if(provider.isEmpty(inventory)) provider.init(player, provider.getContains());
        provider.getContains().execute(e.getSlot(), e);
    }

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new InventoryContains(), plugin);
    }

}
