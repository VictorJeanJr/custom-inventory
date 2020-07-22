package dev.victorjeanjr.custominventory.tester;

import dev.victorjeanjr.custominventory.content.InventoryContains;
import dev.victorjeanjr.custominventory.content.InventoryProvider;
import dev.victorjeanjr.custominventory.objects.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Test extends InventoryProvider {

    public Test() {
        super(Creator.of("§aInventario de Teste", 6*9));
    }

    @Override
    public void init(Player player, InventoryContains contains) {
        contains.fill(3, 2, ClickableItem.of(new ItemStack(Material.CHEST), e -> Bukkit.broadcastMessage("teste")));

        contains.fill(5, 2, ClickableItem.of(new ItemStack(Material.LEATHER_CHESTPLATE), e -> e.setCancelled(true)));

        contains.fill(7, 2, ClickableItem.of(new ItemStack(Material.INK_SACK), e -> player.closeInventory()));

        contains.fillBorder(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
    }

}
