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

public class ItemCore {
    private ItemCore(Material material) {
        this.material = material;
    }

    private String title;
    private String headTexture;

    private Material material;

    private Integer amount = 1;

    private List<String> lore = new ArrayList<>();

    private boolean hideAttributes = true;
    private boolean enchanted = false;

    public static ItemCore of(Material material) {
        return new ItemCore(material);
    }

    public final ItemCore setTitle(String title) {
        this.title = title;
        return this;
    }

    public final ItemCore setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemCore setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemCore setLore(String[] lore) {
        return this.setLore(Arrays.stream(lore).toList());
    }

    public ItemCore addLore(String... loreLines) {
        this.lore.addAll(Arrays.stream(loreLines).toList());
        return this;
    }

    public ItemCore setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public ItemCore setEnchanted(boolean bool) {
        this.enchanted = true;
        return this;
    }

    public final ItemCore hideAttributes(boolean bool) {
        this.hideAttributes = bool;
        return this;
    }

    public final ItemStack craft() {
        final ItemStack item = new ItemStack(this.material, amount);
        final ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (this.title != null)
                meta.setDisplayName(ChatColor.GRAY + Colorizer.color(this.title));
            if (this.hideAttributes)
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            if (this.enchanted) {
                meta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.setLore(lore.stream().map(Colorizer::color).toList());
            item.setItemMeta(meta);
        }

        return item;
    }

    /*
    if (material == Material.PLAYER_HEAD && this.headTexture != null)
        HeadCreator.builder().setTexture(this.headTexture);
    */
}