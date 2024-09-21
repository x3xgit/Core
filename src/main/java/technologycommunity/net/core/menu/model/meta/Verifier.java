package technologycommunity.net.core.menu.model.meta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.constants.ExceptionConstants;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.plugin.Core;

public class Verifier {
    private final ItemStack item;

    public Verifier(final @NotNull ItemStack item) {
        this.item = item;
    }

    public boolean equals(final @NotNull String value) {
        final PersistentDataContainer container = this.getContainer();

        final String containerValue = container.get(Core.getKey(), PersistentDataType.STRING);

        if (containerValue == null)
            throw new CoreException("Tried to check if container has API key and received container value but it's null.");

        return (containerValue.equals(value));
    }

    private @NotNull PersistentDataContainer getContainer() {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null)
            throw new CoreException(ExceptionConstants.MESSAGE.META_IS_NULL);

        return meta.getPersistentDataContainer();
    }
}
