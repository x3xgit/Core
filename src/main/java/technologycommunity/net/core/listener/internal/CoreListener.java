package technologycommunity.net.core.listener.internal;

import org.bukkit.event.Listener;

import technologycommunity.net.core.plugin.Core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
            this.setListener(true);
        }
    }

    public final void unregister() {
        if (this.isRegistered()) {
            this.setListener(false);
        }
    }

    private void setListener(boolean add) {
        if (add) {
            coreListeners.put(this.getClass(), this);
            CoreRegisterer.registerListener(Core.getInstance(), this);
        } else {
            coreListeners.remove(this.getClass(), this);
            CoreRegisterer.stopListener(this);
        }
    }

    private boolean isRegistered() {
        return this.getListeners().contains(this.getClass());
    }

    private Set<Class<? extends CoreListener>> getListeners() {
        return coreListeners.keySet();
    }

    public static Map<Class<? extends CoreListener>, CoreListener> getCoreListeners() {
        return coreListeners;
    }

    protected final void updateListener() {
        this.unregister();
        this.register();
    }
}
