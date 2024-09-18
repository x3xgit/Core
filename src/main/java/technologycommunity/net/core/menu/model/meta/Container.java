package technologycommunity.net.core.menu.model.meta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.constants.ExceptionConstants;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.plugin.Core;

public class Container {
    private final @NotNull ItemStack item;

    private Container(final @NotNull ItemStack item) {
        this.item = item;
    }

    public Container setValue(final @NotNull String value) {
        this.setString(value);

        return this;
    }

    public static Container modify(final @NotNull ItemStack item) {
        return new Container(item);
    }

    public @NotNull ItemMeta modify() {
        if (this.item.getItemMeta() == null)
            throw new CoreException(ExceptionConstants.MESSAGE.META_IS_NULL);

        return this.item.getItemMeta();
    }

    private void setString(final @NotNull String string) {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null)
            throw new CoreException(ExceptionConstants.MESSAGE.META_IS_NULL);

        final PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Core.getKey(), PersistentDataType.STRING, string);

        this.item.setItemMeta(meta);
    }
}
