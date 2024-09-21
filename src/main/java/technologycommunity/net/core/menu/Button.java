package technologycommunity.net.core.menu;

import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.menu.structures.ButtonPosition;
import technologycommunity.net.core.structures.Artist;

public abstract class Button {
    private final ItemStack icon;
    private final ButtonPosition position;

    public Button() {
        this.icon = this.getItem();
        this.position = this.getPosition();
    }

    public abstract void onButtonClick(final @NotNull Artist clicker, final @NotNull Menu menu);
    public abstract @NotNull ButtonPosition getPosition();
    public abstract @NotNull ItemStack getItem();
}
