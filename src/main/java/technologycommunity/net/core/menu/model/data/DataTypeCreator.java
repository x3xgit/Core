package technologycommunity.net.core.menu.model.data;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class DataTypeCreator<T extends Serializable> {
    public PersistentDataObjectType get() {
        return new PersistentDataObjectType();
    }

    private class PersistentDataObjectType implements PersistentDataType<byte[], T> {
        @NotNull
        @Override
        public Class<byte[]> getPrimitiveType() {
            return byte[].class;
        }

        @NotNull
        @Override
        public Class<T> getComplexType() {
            return (Class<T>) Serializable.class;
        }

        @Override
        public byte @NotNull [] toPrimitive(@NotNull T object, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return SerializationUtils.serialize(object);
        }

        @Nullable
        @Override
        public T fromPrimitive(byte @NotNull [] bytes, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            try {
                InputStream is = new ByteArrayInputStream(bytes);
                ObjectInputStream o = new ObjectInputStream(is);

                try {
                    return (T) o.readObject();
                } catch (java.io.IOException | ClassNotFoundException | ClassCastException ex) {
                    return null;
                }
            } catch (IOException e) {
                return null;
            }
        }
    }
}
