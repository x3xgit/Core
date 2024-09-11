package test.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import technologycommunity.net.core.listener.ItemListener;
import test.menu.SpellsBook;

public class MyItemListener extends ItemListener {
    public MyItemListener(ItemStack item) {
        super(item);
    }

    @Override
    protected void onItemClick(Player player, EquipmentSlot equipmentSlot, ItemStack item, Action action) {
        new SpellsBook().displayTo(player);
    }
}
