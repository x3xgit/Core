package technologycommunity.net.core.inventory.buttons;

import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.structures.Position;
import technologycommunity.net.core.structures.Artist;

public abstract class Button {
    private final ItemStack icon;
    private final Position position;

    public Button() {
        this.icon = this.getItem();
        this.position = this.getPosition();
    }

    public abstract void onButtonClick(final Artist clicker, Button button, Menu menu);
    public abstract @NotNull Position getPosition();
    public abstract @NotNull ItemStack getItem();
}
