package test.menu;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.buttons.Button;
import technologycommunity.net.core.inventory.model.ItemCrafter;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.structures.Artist;

public class SpellsBook extends Menu {
    /*
        Здесь будут уровни книг по нажатию которых будут открываться те заклятия которые доступны мне и соответствуют уровню.
    */

    public SpellsBook() {
        this.setTitle("&bSpells book");
        this.setSize(5);

        this.registerButton(
                new Button() {
                    @Override
                    public void onButtonClick(Artist artist, Button button, Menu menu) {
                        artist.tell("&eYou clicked this button for a spell!");
                    }

                    @Override
                    public @NotNull Position getPosition() {
                        return Position.of(13, 1);
                    }

                    @Override
                    public @NotNull ItemStack getItem() {
                        return ItemCrafter.of(Material.ENCHANTED_BOOK)
                                .setTitle("&bSome spell")
                                .craft();
                    }
                }
        );
    }

    @Override
    protected void onMenuOpen(Artist artist, Menu menu) {
        artist.playSound(Sound.ENTITY_PLAYER_LEVELUP);
    }
}
