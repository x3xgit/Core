package technologycommunity.net.core.structures;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.constants.CoreConstants;
import technologycommunity.net.core.exception.CoreException;

import java.util.UUID;

public class Artist {
    private final Player player;

    private Artist(Player player) {
        this.player = player;
    }

    public static Artist of(final Player player) {
        return new Artist(player);
    }

    public static Artist of(final UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);

        if (player == null)
            throw new CoreException("Couldn't find player by UUID. (%s)".formatted(uuid));

        return new Artist(player);
    }

    public static Artist of(String string) {
        final Player player = Bukkit.getPlayer(string);

        if (player == null)
            throw new CoreException("Couldn't find player by String. (%s)".formatted(string));

        return new Artist(player);
    }

    public void tell(final String message) {
        this.player.sendMessage(CoreConstants.COLORS.defaultColor + Corelor.format(message));
    }

    public void tell(final String... messages) {
        for (String message : messages)
            this.tell(message);
    }

    public void playSound(final Sound sound) {
        this.player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public void playSound(final Sound sound, Float volume) {
        if (volume > 10.0f || volume < 0.01f) volume = 1.0f;

        this.player.playSound(player.getLocation(), sound, volume, volume);
    }

    public void kill() {
        this.player.setHealth(0);
    }

    public void revive() {
        this.player.spigot().respawn();
    }

    public boolean isAlive() {
        return !this.isDead();
    }

    public boolean isDead() {
        return this.player.isDead();
    }

    public boolean isSimilar(Player player) {
        return this.player.getUniqueId().equals(player.getUniqueId());
    }

    public void openInventory(Inventory inventory) {
        this.player.openInventory(inventory);
    }

    public void closeInventory() {
        this.player.closeInventory();
    }

    public Player getPlayer() {
        return this.player;
    }
}
