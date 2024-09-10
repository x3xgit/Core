package technologycommunity.net.core.interfaces;

import org.bukkit.command.CommandSender;

public abstract class CoreCommandExecutor {
    public abstract void executeCommand(CommandSender sender, String label, String[] arguments);
}
