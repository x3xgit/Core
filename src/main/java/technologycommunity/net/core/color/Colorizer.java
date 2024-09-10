package technologycommunity.net.core.color;

import org.bukkit.ChatColor;

public class Colorizer {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}