package technologycommunity.net.core.cooldown;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import technologycommunity.net.core.cooldown.structures.Data;
import technologycommunity.net.core.plugin.Core;

import java.util.*;

public class Cooldown {
    private static final HashMap<Data, Double> cooldowns = new HashMap<>();
    private static @Nullable BukkitTask task = null;

    private final @NotNull Data data;
    private final @NotNull Double dsec;

    private Cooldown(final @NotNull Data data, final @NotNull Double dsec) {
        this.data = data;
        this.dsec = dsec;

        cooldowns.put(data, dsec);
    }

    public static Cooldown create(final @NotNull Data data, final @NotNull Double dsec) {
        return new Cooldown(data, dsec);
    }

    public static double get(final @NotNull Data data) {
        for (Map.Entry<Data, Double> entry : cooldowns.entrySet()) {
            final UUID uuid = entry.getKey().getUUID();
            final Object object = entry.getKey().getObject();

            if (uuid.equals(data.getUUID()) && object.equals(data.getObject()))
                return entry.getValue();
        }

        return 0;
    }

    public static void startRunnable() {
        if (task != null && !task.isCancelled())
            return;

        task = new BukkitRunnable() {
            @Override
            public void run() {
                final List<Data> forRemove = new ArrayList<>();

                for (Map.Entry<Data, Double> entry : cooldowns.entrySet()) {
                    if (entry.getValue() > 0.0) {
                        entry.setValue(Math.round((entry.getValue() - 0.1) * 10.0) / 10.0);
                    } else {
                        forRemove.add(entry.getKey());
                    }
                }

                for (Data data : forRemove)
                    cooldowns.remove(data);

                forRemove.clear();
            }
        }.runTaskTimer(Core.getInstance(), 0L, 2L);
    }
}
