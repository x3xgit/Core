package technologycommunity.net.core.interfaces;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CoreTabCompleter {
    public abstract List<String> getArguments(CommandSender sender, String[] arguments);
}
