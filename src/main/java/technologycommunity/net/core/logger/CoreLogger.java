package technologycommunity.net.core.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CoreLogger {
    private CoreLogger() {

    }

    public static CoreLogger getCoreLogger() {
        return new CoreLogger();
    }

    public final void information(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[Core:Information] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public final void error(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Core:Error] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public final void warning(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Core:Warning] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public final void good(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Core:Good] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public final void developer(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Core:Developer] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public final void log(LoggingLevel loggingLevel, String message) {
        switch (loggingLevel) {
            case ERROR:
                error(message);
            case WARNING:
                warning(message);
            case FINE:
                good(message);
            case INFORMATION:
                information(message);
            case DEVELOPER:
                developer(message);
        }
    }
}
