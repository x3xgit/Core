package technologycommunity.net.core.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import technologycommunity.net.core.color.Colorizer;

public class CoreLogger {
    private CoreLogger() {

    }

    public static CoreLogger getCoreLogger() {
        return new CoreLogger();
    }

    public final void information(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[Core::Information] " + Colorizer.color(message));
    }

    public final void error(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Core::Error] " + Colorizer.color(message));
    }

    public final void warning(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Core::Warning] " + Colorizer.color(message));
    }

    public final void good(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Core::Good] " + Colorizer.color(message));
    }

    public final void developer(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[Core::Developer] " + Colorizer.color(message));
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
