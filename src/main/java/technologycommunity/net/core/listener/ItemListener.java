package technologycommunity.net.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import technologycommunity.net.core.event.CoreListener;

public class ItemListener extends CoreListener {
    /*
        The listener is not auto registered, so you have to register it yourself.
     */

    private final ItemStack item;

    public ItemListener(ItemStack item) {
        super(false);

        this.item = item;
    }

    @EventHandler
    public final void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();

        if (item == null) return;

        if (!item.isSimilar(this.item))
            return;

        this.onItemClick(event.getPlayer(), event.getHand(), item, event.getAction());
    }

    protected void onItemClick(Player player, EquipmentSlot equipmentSlot, ItemStack item, Action action) {

    }
}
