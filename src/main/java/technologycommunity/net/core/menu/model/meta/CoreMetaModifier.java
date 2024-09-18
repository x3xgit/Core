package technologycommunity.net.core.menu.model.meta;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.menu.model.SkullCore;
import technologycommunity.net.core.plugin.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreMetaModifier {
    private final ItemStack item;
    private final ItemMeta meta;

    private final Corelor defaultColor = Corelor.GRAY;

    private CoreMetaModifier(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public static CoreMetaModifier get(ItemStack item) {
        return new CoreMetaModifier(item);
    }

    public CoreMetaModifier setName(final @NotNull String title) {
        this.meta.setDisplayName(this.defaultColor + Corelor.format(title));
        return this;
    }

    @Deprecated
    public CoreMetaModifier addLore(final @NotNull String... lore) {
        final List<String> oldLore = this.meta.getLore() == null ? new ArrayList<>() : this.meta.getLore();

        for (String line : lore)
            oldLore.add(this.configure(line));

        this.meta.setLore(oldLore);
        return this;
    }

    public CoreMetaModifier setLore(final @NotNull String... lore) {
        this.meta.setLore(Arrays.stream(lore).map(this::configure).toList());
        return this;
    }

    public CoreMetaModifier setLore(final @NotNull List<String> lore) {
        this.meta.setLore(lore.stream().map(this::configure).toList());
        return this;
    }

    public CoreMetaModifier addEnchant(final @NotNull Enchantment enchantment, final @NotNull Integer level, final @NotNull Boolean ignoreLevelRestriction) {
        this.meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public CoreMetaModifier addItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.meta.addItemFlags(itemFlags);
        return this;
    }

    public CoreMetaModifier removeItemFlags(final @NotNull ItemFlag... itemFlags) {
        this.meta.removeItemFlags(itemFlags);
        return this;
    }

    public CoreMetaModifier setUnbreakable(final boolean to) {
        this.meta.setUnbreakable(to);
        return this;
    }

    public CoreMetaModifier setSkullTexture(final @NotNull SkullModifier modifier, final @NotNull String string) {
        if (!this.item.getType().equals(Material.PLAYER_HEAD))
            return this;

        this.item.setItemMeta(SkullCore.modify(this.meta)
                .setTexture(modifier, string)
            .get());

        return this;
    }

    public CoreMetaModifier setPersistentDataValue(PersistentDataType<Object, Object> type, Object information) {
        this.meta.getPersistentDataContainer().set(new NamespacedKey(Core.getInstance(), Core.getNamed()), type, information);
        return this;
    }

    public CoreMetaModifier setEnchantmentEffect(final boolean is) {
        if (is)
            this.meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        else
            if (this.meta.hasEnchant(Enchantment.KNOCKBACK) && this.meta.getEnchants().get(Enchantment.KNOCKBACK) == 1)
                this.meta.removeEnchant(Enchantment.KNOCKBACK);

        return this;
    }

    public ItemMeta configure() {
        return this.meta;
    }

    private String configure(final @NotNull String text) {
        return this.defaultColor + Corelor.format(text);
    }
}
