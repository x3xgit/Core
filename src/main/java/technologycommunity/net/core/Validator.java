package technologycommunity.net.core;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.structures.Artist;

public class Validator {
    public static boolean checkNotNull(final @Nullable Object object, final boolean ex) {
        if (object == null)
            if (ex) throw new CoreException("Generated CoreException, checked object is null.");
            else return false;

        return true;
    }

    public static boolean checkNotNull(final @Nullable Object object) {
        return checkNotNull(object, false);
    }

    public static boolean checkPlayerIsArtist(final @NotNull Artist artist, final @NotNull Player player) {
        return player.getUniqueId().equals(artist.getPlayer().getUniqueId());
    }
}
