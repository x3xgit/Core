package technologycommunity.net.core.map;

import org.bukkit.entity.Player;
import org.bukkit.map.*;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class MapRender extends MapRenderer {
    final BufferedImage image;

    @ApiStatus.Experimental
    final boolean loadOnce = true;
    boolean isLoaded = false;

    public MapRender(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
        if (this.isLoaded)
            return;

        mapCanvas.drawImage(0, 0, image);

        this.isLoaded = true;
    }
}
