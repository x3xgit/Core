package technologycommunity.net.core.inventory.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class SkullCore {
    final ItemStack item;

    public SkullCore() {
        this.item = new ItemStack(Material.PLAYER_HEAD);
    }

    public ItemStack craft() {
        return item;
    }
}
