package technologycommunity.net.core.menu.model;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.menu.model.meta.TextureType;
import technologycommunity.net.core.plugin.Core;

import java.util.Base64;
import java.util.UUID;

public class SkullCreator {
    private final SkullMeta meta;

    private SkullCreator(SkullMeta meta) {
        this.meta = meta;
    }

    public static SkullCreator modify(ItemMeta meta) {
        if (!(meta instanceof SkullMeta skullMeta))
            throw new CoreException("Can't modify anything except skull.");

        return new SkullCreator(skullMeta);
    }

    public SkullMeta get() {
        return meta;
    }

    public SkullCreator setTexture(final @NotNull TextureType modifier, final @NotNull String string) {
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
            final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            final ProfileProperty property = new ProfileProperty("textures", base64);

            profile.setProperty(property);

            meta.setPlayerProfile(profile);
        } catch (RuntimeException ex) {
            Core.getInstance().getCoreLogger().warning("Couldn't set texture with BASE64: " + ex.getMessage());
        }
    }

    protected void setTextureWithNickname(final @NotNull String nickname) {
        try {
            meta.setOwnerProfile(Bukkit.getOfflinePlayer(nickname).getPlayerProfile());
        } catch (RuntimeException ex) {
            Core.getInstance().getCoreLogger().warning("Couldn't set texture with nickname: " + ex.getMessage());
        }
    }

    protected @NotNull String encodeUrlToBase64(final @NotNull String url) {
        return Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes());
    }
}
