package test.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.menu.Menu;
import technologycommunity.net.core.menu.Button;
import technologycommunity.net.core.menu.model.ItemCore;
import technologycommunity.net.core.menu.structures.ButtonPosition;
import technologycommunity.net.core.structures.Artist;

public class SecondMenu extends Menu {
    public SecondMenu() {
        this.setSize(3);

        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(final @NotNull Artist clicker, final @NotNull Menu menu) {
                        clicker.tell("&aThis is SecondMenu, I'm closing it...");
                        //clicker.closeInventory();
                    }

                    @Override
                    public @NotNull ButtonPosition getPosition() {
                        return ButtonPosition.of(13, 1);
                    }

                    @Override
                    public @NotNull ItemStack getItem() {
                        return ItemCore.of(Material.TNT).create();
                    }
                }
        );

        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(final @NotNull Artist clicker, final @NotNull Menu menu) {
                        clicker.tell("&aThis is SecondMenu, I'm closing it...");
                        clicker.closeInventory();
                    }

                    @Override
                    public @NotNull ButtonPosition getPosition() {
                        return ButtonPosition.of(13, 1);
                    }

                    @Override
                    public @NotNull ItemStack getItem() {
                        return ItemCore.of(Material.TNT).create();
                    }
                }
        );
    }

    @Override
    public void onMenuOpen(final @NotNull Artist artist, final @NotNull Menu menu) {
        artist.tell("&7SecondMenu menu is opened.");
    }
}
