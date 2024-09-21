package technologycommunity.net.core.menu.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.menu.model.data.DataTypeCreator;
import technologycommunity.net.core.menu.model.meta.CoreMetaModifier;
import technologycommunity.net.core.menu.model.meta.SkullModifier;

import java.io.Serializable;
import java.util.List;

public class ItemCore {
    private final ItemStack item;

    private final Corelor defaultColor = Corelor.GRAY;

    private ItemCore(final @NotNull ItemStack item) {
        this.item = item;
    }

    private ItemCore(final @NotNull Material material, final @NotNull Integer amount) {
        this(new ItemStack(material, (amount > 0 && amount < 64) ? amount : 1 ));
    }

    public static ItemCore of(final @NotNull Material material, final @NotNull Integer amount) {
        return new ItemCore(material, amount);
    }

    public static ItemCore of(final @NotNull Material material) {
        return new ItemCore(material, 1);
    }

    public static ItemCore of(final @NotNull ItemStack item) {
        return new ItemCore(item);
    }

    public ItemCore name(final @NotNull String title) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                        .setName(title)
                .configure());
        return this;
    }

    public ItemCore lore(final @NotNull String... lore) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                .setLore(lore)
                .configure());

        return this;
    }

    public ItemCore lore(final @NotNull List<String> lore) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                        .setLore(lore)
                .configure());

        return this;
    }

    public ItemCore amount(final @NotNull Integer amount) {
        this.item.setAmount(amount);

        return this;
    }

    public ItemCore enchanted(final boolean bool) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                        .setEnchantmentEffect(bool)
                .configure());

        return this;
    }

    public ItemCore addItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                        .addItemFlags(itemFlags)
                .configure());

        return this;
    }

    public ItemCore removeItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                .removeItemFlags(itemFlags)
            .configure());

        return this;
    }

    public ItemCore skull(final @NotNull SkullModifier modifier, final @NotNull String string) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                .setSkullTexture(modifier, string)
            .configure());

        return this;
    }

    public <T extends Serializable> ItemCore data(final @NotNull DataTypeCreator<T> type, final @NotNull T object) {
        this.item.setItemMeta(CoreMetaModifier.get(this.item)
                .setData(type, object)
            .configure());

        return this;
    }

    public final ItemStack create() {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta != null && !meta.hasDisplayName())
            this.item.setItemMeta(CoreMetaModifier.get(this.item)
                    .setName(this.item.getType().getTranslationKey())
                .configure());

        return this.item;
    }

    /*
    if (material == Material.PLAYER_HEAD && this.headTexture != null)
        HeadCreator.builder().setTexture(this.headTexture);
    */
}