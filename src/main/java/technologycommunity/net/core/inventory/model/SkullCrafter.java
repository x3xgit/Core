package technologycommunity.net.core.inventory.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkullCrafter {
    final ItemStack item;

    public SkullCrafter() {
        this.item = new ItemStack(Material.PLAYER_HEAD);
    }

    public ItemStack craft() {
        return item;
    }
}
