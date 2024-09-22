package technologycommunity.net.core.menu.model;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import technologycommunity.net.core.constants.ExceptionConstants;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.menu.model.meta.SkullModifier;
import technologycommunity.net.core.plugin.Core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class SkullCore {
    private final SkullMeta meta;

    private SkullCore(SkullMeta meta) {
        this.meta = meta;
    }

    public static SkullCore modify(ItemMeta meta) {
        if (!(meta instanceof SkullMeta skullMeta))
            throw new CoreException("Can't modify anything except skull.");

        return new SkullCore(skullMeta);
    }

    public SkullMeta get() {
        return meta;
    }

    public SkullCore setTexture(final @NotNull SkullModifier modifier, final @NotNull String string) {
        switch (modifier) {
            case URL:
                setTextureWithBase64(encodeUrlToBase64(string));
                break;
            case BASE64:
                setTextureWithBase64(string);
                break;
            case NICKNAME:
                setTextureWithNickname(string);
                break;
        }

        return this;
    }

    protected void setTextureWithBase64(final @NotNull String base64) {
        try {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), base64);
            profile.getProperties().put("textures", new Property("textures", base64));

            try {
                final Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                throw new CoreException("Couldn't get BASE64 because field is not found.");
            }
        } catch (RuntimeException ex) {
            Core.getInstance().getCoreLogger().error("Couldn't set texture with BASE64: " + ex.getMessage());
        }
    }

    protected void setTextureWithNickname(final @NotNull String nickname) {
        try {
            meta.setOwnerProfile(Bukkit.getOfflinePlayer(nickname).getPlayerProfile());
        } catch (RuntimeException ex) {
            Core.getInstance().getCoreLogger().error("Couldn't set texture with nickname: " + ex.getMessage());
        }

    }

    protected @NotNull String encodeUrlToBase64(final @NotNull String url) {
        return Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes());
    }
}
