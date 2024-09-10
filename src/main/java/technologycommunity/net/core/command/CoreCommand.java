package technologycommunity.net.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.color.Colorizer;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.plugin.Core;

import java.lang.reflect.Field;
import java.util.List;

public class CoreCommand extends Command {
    private String commandPermission = null;

    private String playerOnlyMessage = Colorizer.color("&cThis command is only for players.");
    private String permissionMessage = Colorizer.color("&cYou don't have a permission.");

    private String label;

    public CoreCommand(@NotNull String label) {
        super(label);

        this.label = label;
    }

    public final void register() {
        CommandMap commandMap = this.getCommandMap();

        if (commandMap == null)
            throw new CoreException("Couldn't get command map, command was not registered.");

        if (commandMap.getCommand(this.label) != null)
            throw new CoreException("Couldn't register this command because its already registered.");

        commandMap.register(
                Core.getInstance().getDescription().getName().toLowerCase(), this);
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            return null;
        }
    }

    protected void onCommandExecute(CommandSender sender, String label, String[] arguments) {

    }

    /*
        Deprecated for right now, because it doesn't work as it has to.
     */
    @Deprecated
    protected @NotNull List<String> getArguments(CommandSender sender, String label, String[] arguments) {
        return List.of();
    }

    protected Boolean isPlayerOnly() {
        return false;
    }

    @Override
    public final boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandPermission != null)
            if (!commandSender.hasPermission(commandPermission)) {
                commandSender.sendMessage(permissionMessage);
                return true;
            }
        if (this.isPlayerOnly())
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(playerOnlyMessage);
                return true;
            }

        this.onCommandExecute(commandSender, s, strings);

        return true;
    }
}
