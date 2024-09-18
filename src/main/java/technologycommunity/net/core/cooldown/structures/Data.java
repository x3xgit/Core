package technologycommunity.net.core.cooldown.structures;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Data {
    private final UUID uuid;
    private final Object object;

    public Data(final @NotNull UUID uuid, final @NotNull Object object) {
        this.uuid = uuid;
        this.object = object;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Object getObject() {
        return object;
    }
}
