package technologycommunity.net.core.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.plugin.Core;

public class CoreLogger {
    private CoreLogger() {

    }

    public static CoreLogger getCoreLogger() {
        return new CoreLogger();
    }

    public final void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY +  Corelor.format(message));
    }

    public final void error(String message) {
        Core.getInstance().getLogger().severe(message);
    }

    public final void warning(String message) {
        Core.getInstance().getLogger().warning(message);
    }
}
