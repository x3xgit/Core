package test.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.command.CoreCommand;

import technologycommunity.net.core.map.MapCreator;

import java.util.List;

public class GetMap extends CoreCommand {
    public GetMap() {
        super("getmap");
    }

    @Override
    protected void onCommandExecute(final CommandSender sender, final String label, final String[] arguments) {
        if (!(sender instanceof Player player))
            return;
        if (arguments.length < 1)
            return;

        final MapCreator map = MapCreator.of(arguments[0]);
        final ItemStack item = map.getItem();

        if (item == null) {
            player.sendMessage(ChatColor.RED + "Couldn't load image.");
            return;
        }

        player.getInventory().addItem(item);
    }

    @Override
    protected @NotNull List<String> getArguments(final CommandSender sender, final String label, final String[] arguments) {
        if (arguments.length == 1)
            return List.of("https://");
        return List.of();
    }
}
