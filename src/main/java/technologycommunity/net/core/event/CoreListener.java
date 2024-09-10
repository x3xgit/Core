package technologycommunity.net.core.event;

import org.bukkit.event.Listener;

import technologycommunity.net.core.plugin.Core;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoreListener implements Listener {
    private static final Map<Class<? extends CoreListener>, CoreListener> coreListeners = new LinkedHashMap<>();

    private boolean isRegistered = false;
    private Listener listener;

    public CoreListener(boolean autoRegister) {
        if (autoRegister)
            this.register();
    }

    public CoreListener() {
        new CoreListener(false);
    }

    protected final void register() {
        if (this.isRegistered)
            return;

        this.listener = this;

        if (!coreListeners.containsKey(this.getClass())) {
            CoreRegisterer.registerListener(Core.getInstance(), this);
            coreListeners.put(this.getClass(), this);
        }

        this.isRegistered = true;
    }

    protected final void unregister() {
        if (!isRegistered)
            return;

        this.listener = null;

        coreListeners.remove(this.getClass(), this);
        CoreRegisterer.stopListener(this);
    }

    public static Map<Class<? extends CoreListener>, CoreListener> getCoreListeners() {
        return coreListeners;
    }

    protected final void updateListener() {
        this.unregister();
        this.register();
    }
}
