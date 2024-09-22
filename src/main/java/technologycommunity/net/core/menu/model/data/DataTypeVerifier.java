package technologycommunity.net.core.menu.model.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.constants.ExceptionConstants;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.plugin.Core;

import java.io.Serializable;

public class DataTypeVerifier {
    private final ItemStack item;

    public DataTypeVerifier(final @NotNull ItemStack item) {
        this.item = item;
    }

    public <T extends Serializable> @Nullable T get(final @NotNull DataTypeCreator<T> type) {
        final PersistentDataContainer container = this.getContainer();

        return container.get(Core.getKey(), type.get());
    }

    private @NotNull PersistentDataContainer getContainer() {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null)
            throw new CoreException(ExceptionConstants.MESSAGE.META_IS_NULL);

        return meta.getPersistentDataContainer();
    }
}
