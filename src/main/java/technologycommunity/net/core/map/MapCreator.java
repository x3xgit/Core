package technologycommunity.net.core.map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Nullable;

import technologycommunity.net.core.constants.ExceptionConstants;
import technologycommunity.net.core.exception.CoreException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MapCreator {
    final BufferedImage image;
    final String url;
    final ItemStack item;
    final MapView view;

    private MapCreator(String url) {
        if (Bukkit.getWorlds().isEmpty())
            throw new CoreException("Server worlds are empty. Couldn't create a map.");

        this.url = url;

        if (!this.canLoad(url)) {
            this.item = null;
            this.image = null;
            this.view = null;

            return;
        }

        this.image = this.getFromURL(url);
        this.view = this.createMapView();

        this.view.getRenderers().clear();
        this.view.addRenderer(new MapRender(image));

        this.item = this.generateMap(view);
    }

    public static MapCreator of(String url) {
        return new MapCreator(url);
    }

    private boolean canLoad(String url) {
        return (getFromURL(url) != null);
    }

    private @Nullable BufferedImage getFromURL(String url) {
        BufferedImage image;

        try {
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image);

            return image;
        } catch (IOException ex) {
            return null;
        }
    }

    private MapView createMapView() {
        return Bukkit.createMap(Bukkit.getWorlds().getFirst());
    }

    private ItemStack generateMap(MapView view) {
        ItemStack item = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) item.getItemMeta();

        if (meta == null)
            throw new CoreException(ExceptionConstants.MESSAGE.META_IS_NULL);

        meta.setMapView(view);
        item.setItemMeta(meta);

        return item;
    }

    public @Nullable ItemStack getItem() {
        return this.item;
    }
}
