package technologycommunity.net.core.listener.internal;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import technologycommunity.net.core.plugin.Core;

import java.util.ArrayList;
import java.util.List;

public class CoreRegisterer {
    public static List<CoreRegisterer> registeredListeners = new ArrayList<>();

    private final Core instance;
    private final Listener listener;

    private CoreRegisterer(Core instance, Listener listener) {
        this.instance = instance;
        this.listener = listener;

        Bukkit.getPluginManager().registerEvents(listener, instance);
        registeredListeners.add(this);
    }

    public static CoreRegisterer registerListener(Core instance, Listener listener) {
        return new CoreRegisterer(instance, listener);
    }

    public static void stopListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }
}
