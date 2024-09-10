package technologycommunity.net.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import technologycommunity.net.core.event.CoreListener;

public class ItemListener extends CoreListener {
    private final ItemStack item;

    public ItemListener(ItemStack item) {
        super(true);

        this.item = item;
    }

    @EventHandler
    public final void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();

        if (item == null) return;

        if (!item.isSimilar(this.item))
            return;

        this.onItemClick(item, event.getAction());
    }

    protected void onItemClick(ItemStack item, Action action) {

    }
}
