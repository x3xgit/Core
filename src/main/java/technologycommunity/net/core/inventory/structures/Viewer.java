package technologycommunity.net.core.inventory.structures;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.color.Colorizer;
import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.plugin.Core;

import java.util.Arrays;

public class Viewer {
    private final Player player;
    private boolean updating = false;
    private final Menu menu;

    public Viewer(Player player, Menu menu) {
        this.player = player;
        this.menu = menu;
    }

    public void tell(String... messages) {
        for (String message : Arrays.stream(messages).map(Colorizer::color).toList())
            this.player.sendMessage(message);
    }

    public void play(Sound sound) {
        this.player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public void play(Sound sound, Float volume) {
        this.player.playSound(player.getLocation(), sound, volume, volume);
    }

    public @Nullable InventoryView getOpenInventory() {
        return this.player.getOpenInventory();
    }

    public void closeMenu() {
        this.updating = false;
        this.player.closeInventory();
    }

    public void reOpenMenu() {
        this.update(() -> this.player.openInventory(menu.getInventory(menu.getPage())));
    }

    public void openMenu() {
        if (menu.getPages().isEmpty())
            menu.drawPages();

        this.update(() -> this.player.openInventory(menu.getInventory(menu.getFirstPage())));
    }

    public void goParent() {
        final Menu parent = menu.getParentMenu();

        this.update(() -> this.player.openInventory(parent.getInventory(parent.getFirstPage())));
    }

    public boolean isUpdating() {
        return this.updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public boolean isSimilar(Player player) {
        return (player.getUniqueId().equals(this.player.getUniqueId()));
    }

    public boolean isSimilar(Viewer viewer) {
        return (viewer.equals(this));
    }

    private void update(final Runnable task) {
        this.updating = true;
        task.run();
        this.updating = false;
    }
}
