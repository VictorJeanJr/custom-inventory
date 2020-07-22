package dev.victorjeanjr.custominventory.objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> consumer;

    private ClickableItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getConsumer() {
        return consumer;
    }

    public static ClickableItem of(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        return new ClickableItem(itemStack, consumer);
    }

    public static ClickableItem empty(ItemStack itemStack) {
        return new ClickableItem(itemStack, e -> {});
    }


}
