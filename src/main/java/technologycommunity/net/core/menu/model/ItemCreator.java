package technologycommunity.net.core.menu.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.menu.model.data.DataTypeCreator;
import technologycommunity.net.core.menu.model.meta.MetaEditor;
import technologycommunity.net.core.menu.model.meta.TextureType;

import java.io.Serializable;
import java.util.List;

public class ItemCreator {
    private final ItemStack item;

    private final Corelor defaultColor = Corelor.GRAY;

    private ItemCreator(final @NotNull ItemStack item) {
        this.item = item;
    }

    private ItemCreator(final @NotNull Material material, final @NotNull Integer amount) {
        this(new ItemStack(material, (amount > 0 && amount < 64) ? amount : 1 ));
    }

    public static ItemCreator of(final @NotNull Material material, final @NotNull Integer amount) {
        return new ItemCreator(material, amount);
    }

    public static ItemCreator of(final @NotNull Material material) {
        return new ItemCreator(material, 1);
    }

    public static ItemCreator of(final @NotNull ItemStack item) {
        return new ItemCreator(item);
    }

    public ItemCreator name(final @NotNull String title) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                        .setName(title)
                .configure());
        return this;
    }

    public ItemCreator lore(final @NotNull String... lore) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                .setLore(lore)
                .configure());

        return this;
    }

    public ItemCreator lore(final @NotNull List<String> lore) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                        .setLore(lore)
                .configure());

        return this;
    }

    public ItemCreator amount(final @NotNull Integer amount) {
        this.item.setAmount(amount);

        return this;
    }

    public ItemCreator enchanted(final boolean bool) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                        .setEnchantmentEffect(bool)
                .configure());

        return this;
    }

    public ItemCreator addItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                        .addItemFlags(itemFlags)
                .configure());

        return this;
    }

    public ItemCreator removeItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                .removeItemFlags(itemFlags)
            .configure());

        return this;
    }

    public ItemCreator skull(final @NotNull TextureType modifier, final @NotNull String string) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                .setSkullTexture(modifier, string)
            .configure());

        return this;
    }

    public <T extends Serializable> ItemCreator data(final @NotNull DataTypeCreator<T> type, final @NotNull T object) {
        this.item.setItemMeta(MetaEditor.get(this.item)
                .setData(type, object)
            .configure());

        return this;
    }

    public final ItemStack create() {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta != null && !meta.hasDisplayName())
            this.item.setItemMeta(MetaEditor.get(this.item)
                    .setName(this.item.getType().name())
                .configure());

        return this.item;
    }

    /*
    if (material == Material.PLAYER_HEAD && this.headTexture != null)
        HeadCreator.builder().setTexture(this.headTexture);
    */
}