package test.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.command.CoreCommand;
import technologycommunity.net.core.map.MapCreator;

public class GetMap extends CoreCommand {
    public GetMap() {
        super("getmap");
    }

    @Override
    protected boolean onCommand(@NotNull Player executor, @NotNull String label, @NotNull String[] arguments) {
        if (arguments.length < 1)
            return false;

        final MapCreator map = MapCreator.of(arguments[0]);
        final ItemStack item = map.getItem();

        if (item != null) {
            executor.getInventory().addItem(item);
            return true;
        }

        executor.sendMessage(ChatColor.RED + "Couldn't load image.");
        return false;
    }

    @Override
    protected double getCooldown(@NotNull Player player, @NotNull String[] arguments) {
        return 7;
    }
}
