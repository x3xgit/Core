package technologycommunity.net.core.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import technologycommunity.net.core.plugin.Core;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoreListener implements Listener {
    private static final Map<Class<? extends CoreListener>, CoreListener> coreListeners = new LinkedHashMap<>();

    private final boolean isAutoRegister;
    private Listener listener;

    public CoreListener(final boolean autoRegister) {
        this.isAutoRegister = autoRegister;

        if (autoRegister)
            this.register();
    }

    public CoreListener() {
        this(false);
    }

    public final void register() {
        if (!this.isRegistered()) {
            this.listener = this;

            CoreRegisterer.registerListener(Core.getInstance(), this);
            coreListeners.put(this.getClass(), this);
        }
    }

    public final void unregister() {
        if (this.isRegistered()) {
            this.listener = null;

            coreListeners.remove(this.getClass(), this);
            CoreRegisterer.stopListener(this);
        }
    }

    private boolean isRegistered() {
        return coreListeners.containsKey(this.getClass()) && this.listener != null;
    }

    public static Map<Class<? extends CoreListener>, CoreListener> getCoreListeners() {
        return coreListeners;
    }

    protected final void updateListener() {
        this.unregister();
        this.register();
    }
}
