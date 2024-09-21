package test.types;

import java.io.Serializable;
import java.util.UUID;

public class SecretTestType implements Serializable {
    private final UUID uuid;

    public SecretTestType() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return uuid;
    }
}
