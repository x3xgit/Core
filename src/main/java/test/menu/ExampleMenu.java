package test.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.menu.Menu;
import technologycommunity.net.core.menu.Button;
import technologycommunity.net.core.menu.model.ItemCore;
import technologycommunity.net.core.menu.model.meta.SkullModifier;
import technologycommunity.net.core.menu.structures.ButtonPosition;
import technologycommunity.net.core.structures.Artist;

public class ExampleMenu extends Menu {
    private final String headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRiOTk1ZDgxOGE2ZjlhY2VjZTk4YWZhMmEyZjZlYjIyNTYxNWU2YTRlMWUyM2Y5YmY5YjZkMWUwZmQyZDI3In19fQ==";
    private final String headURL = "http://textures.minecraft.net/texture/5790a07da1793df0a591366b8506845f372ef8f4a0327f2f3fe3b3089e1d52d3";

    public ExampleMenu(final Player player) {
        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(final Artist clicker, final Menu menu) {
                        clicker.tell("&aThis is ExampleMenu, displaying to SecondMenu()...");
                        new SecondMenu().displayTo(clicker.getPlayer());
                    }

                    @Override
                    public @NotNull ButtonPosition getPosition() {
                        return ButtonPosition.of(13, 1);
                    }

                    @Override
                    public @NotNull ItemStack getItem() {
                        return ItemCore.of(Material.PLAYER_HEAD)
                                .name("&eLol")
                                .skull(SkullModifier.URL, headURL)
                                .create();
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
