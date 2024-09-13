package test.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.model.ItemCrafter;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.structures.Artist;

public class SecondMenu extends Menu {
    public SecondMenu() {
        this.setSize(3);

        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(final Artist clicker, final Menu menu) {
                        clicker.tell("&aThis is SecondMenu, I'm closing it...");
                        //clicker.closeInventory();
                    }

                    @Override
                    public @NotNull Position getPosition() {
                        return Position.of(13, 1);
                    }

                    @Override
                    public @NotNull ItemStack getItem() {
                        return ItemCrafter.of(Material.TNT).craft();
                    }
                }
        );
    }

    @Override
    public void onMenuOpen(final Artist artist, final Menu menu) {
        artist.tell("&7SecondMenu menu is opened.");
    }
}
