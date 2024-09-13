package test.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.model.ItemCrafter;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.structures.Artist;

public class ExampleMenu extends Menu {
    public ExampleMenu(final Player player) {
        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(Artist clicker, Button button, Menu menu) {
                        clicker.tell("&aThis is ExampleMenu, displaying to SecondMenu()...");
                        new SecondMenu().displayTo(clicker.getPlayer());
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

        this.displayTo(player);
    }

    @Override
    public void onMenuOpen(Artist artist, Menu menu) {
        artist.tell("&7ExampleMenu menu is opened.");
    }
}
