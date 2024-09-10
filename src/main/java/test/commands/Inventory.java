package test.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import technologycommunity.net.core.command.CoreCommand;
import technologycommunity.net.core.inventory.Menu;

import test.menu.ExampleMenu;

public class Inventory extends CoreCommand {
    public Inventory() {
        super("inventory");
    }

    @Override
    protected void executeCommand(final CommandSender sender, final String label, final String[] arguments) {
        if (!(sender instanceof Player player))
            return;

        final Menu menu = new ExampleMenu(player);
    }
}
