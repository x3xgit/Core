package technologycommunity.net.core;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import technologycommunity.net.core.menu.Menu;
import technologycommunity.net.core.menu.structures.RegisteredMenu;
import technologycommunity.net.core.structures.Artist;

import java.util.HashMap;
import java.util.Map;

public class Validator {
    private static boolean isSimilar(Map<Integer, ItemStack> firstInventory, Map<Integer, ItemStack> secondInventory) {
        if (firstInventory.size() != secondInventory.size()) return false;

        for (Integer slot : firstInventory.keySet())
            if (!secondInventory.containsKey(slot) || !firstInventory.get(slot).isSimilar(secondInventory.get(slot)))
                return false;

        return true;
    }

    public static boolean isMenu(Inventory inventory) {
        for (RegisteredMenu registeredMenu : Menu.getRegisteredMenuList())
        {
            Map<Integer, ItemStack> inventoryItems = new HashMap<>();

            for (int slot = 0; slot < inventory.getSize(); slot++)
                if (inventory.getItem(slot) != null)
                    inventoryItems.put(slot, inventory.getItem(slot));

            for (Map.Entry<Integer, Inventory> entry : registeredMenu.getMenuObject().getPages().entrySet()) {
                Map<Integer, ItemStack> pageItems = new HashMap<>();

                for (int slot = 0; slot < entry.getValue().getSize(); slot++)
                    if (entry.getValue().getItem(slot) != null)
                        pageItems.put(slot, entry.getValue().getItem(slot));

                if (isSimilar(inventoryItems, pageItems))
                    return true;
            }
        }

        return false;
    }

    public static boolean valid(Artist artist) {
        if (artist == null)
            return false;

        return true;
    }

    public static boolean valid(Artist artist, Player player) {
        return player.getUniqueId().equals(artist.getPlayer().getUniqueId());
    }
}
