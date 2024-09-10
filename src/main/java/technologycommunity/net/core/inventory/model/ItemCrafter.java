package technologycommunity.net.core.inventory.model;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import technologycommunity.net.core.color.Colorizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCrafter {
    protected ItemCrafter(Material material) {
        this.material = material;
    }

    private String title;
    private String headTexture;

    private Material material;

    private Integer amount = 1;

    private List<String> lore = new ArrayList<>();

    private boolean hideAttributes = true;
    private boolean enchanted = false;


    public static ItemCrafter of(Material material) {
        return new ItemCrafter(material);
    }

    public final ItemCrafter setTitle(String title) {
        this.title = title;
        return this;
    }

    public final ItemCrafter setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemCrafter setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemCrafter addLore(String... loreLines) {
        this.lore.addAll(Arrays.stream(loreLines).toList());
        return this;
    }

    public ItemCrafter setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public ItemCrafter setEnchanted(boolean bool) {
        this.enchanted = true;
        return this;
    }

    public final ItemCrafter hideAttributes(boolean bool) {
        this.hideAttributes = bool;
        return this;
    }

    public final ItemStack craft() {
        ItemStack item = new ItemStack(this.material, amount);

        /*if (material == Material.PLAYER_HEAD && this.headTexture != null)
            HeadCreator.builder().setTexture(this.headTexture);*/

        ItemMeta meta = item.getItemMeta();

        if (this.enchanted)
            item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 0);

        if (meta != null) {
            if (this.title != null)
                meta.setDisplayName(ChatColor.GRAY + Colorizer.color(this.title));
            if (this.hideAttributes)
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if (this.enchanted)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            meta.setLore(lore.stream().map(Colorizer::color).toList());

            item.setItemMeta(meta);
        }

        return item;
    }
}