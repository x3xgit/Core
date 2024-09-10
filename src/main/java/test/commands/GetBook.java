package test.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import technologycommunity.net.core.command.CoreCommand;
import test.Plugin;

public class GetBook extends CoreCommand {
    public GetBook() {
        super("getbook");
    }

    @Override
    protected void onCommandExecute(final CommandSender sender, final String label, final String[] arguments) {
        if (!(sender instanceof Player player))
            return;

        player.getInventory().addItem(Plugin.getInstance().item);
    }
}