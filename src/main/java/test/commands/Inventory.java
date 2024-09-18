package test.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.command.CoreCommand;
import technologycommunity.net.core.menu.Menu;

import test.menu.ExampleMenu;

public class Inventory extends CoreCommand {
    public Inventory() {
        super("inventory");
    }

    @Override
    protected boolean onCommand(@NotNull Player executor, @NotNull String label, @NotNull String[] arguments) {
        final Menu menu = new ExampleMenu(executor);

        return true;
    }
}
