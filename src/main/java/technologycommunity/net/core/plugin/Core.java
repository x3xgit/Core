package technologycommunity.net.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import technologycommunity.net.core.inventory.Menu;
import technologycommunity.net.core.inventory.listener.MenuListener;
import technologycommunity.net.core.plugin.structures.PluginStatus;
import technologycommunity.net.core.logger.CoreLogger;

import java.io.File;
import java.util.Objects;

public class Core extends JavaPlugin {
    static NamespacedKey key;
    static Core instance;
    PluginStatus status;

    public static Core getInstance() {
        if (instance == null) {
            try {
                instance = JavaPlugin.getPlugin(Core.class);
            } catch (final IllegalStateException ex) {
                Bukkit.getLogger().severe("Failed to get instance of the plugin, you need to do a clean restart instead.");
                throw ex;
            }

            Objects.requireNonNull(instance, "Cannot get a new instance! Have you reloaded?");
        }

        return instance;
    }

    @Override
    public final void onEnable() {
        if (!this.isEnabled())
            return;

        this.initialize();

        this.status = PluginStatus.STARTED;
        this.onStart();
    }

    @Override
    public final void onLoad() {
        this.status = PluginStatus.STARTING;
        this.onPreStart();
    }

    @Override
    public final void onDisable() {
        this.status = PluginStatus.FINISHING;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            if (Menu.getPlayerMenu(onlinePlayer) != null) {
                Menu.rejectPlayerMenu(onlinePlayer);
                onlinePlayer.closeInventory();
            }

        this.onFinish();
        this.status = PluginStatus.FINISHED;
    }

    protected final void initialize() {
        instance = this;
        key = new NamespacedKey(instance, "plugin");

        new MenuListener().register();
    }

    public final void savePluginResource(String resourcePath) {
        File configuration = new File(instance.getDataFolder(), resourcePath);

        if (!configuration.exists()) {
            boolean isDirectoryCreated = configuration.getParentFile().mkdirs();
            if (isDirectoryCreated)
                instance.saveResource(resourcePath, false); else getLogger().severe("Couldn't save plugin resource because directory didn't create.");
        }
    }

    public final YamlConfiguration getPluginResource(String resourcePath) {

        File configuration = new File(instance.getDataFolder(), resourcePath);

        if (!isResourceExist(resourcePath))
            savePluginResource(resourcePath);

        return YamlConfiguration.loadConfiguration(configuration);
    }

    protected static boolean isResourceExist(String resourcePath) {
        return new File(instance.getDataFolder(), resourcePath).exists();
    }

    protected void onStart() {

    }

    protected void onFinish() {

    }

    protected void onPreStart() {

    }

    public static NamespacedKey getKey() {
        return key;
    }

    public static boolean hasKey(ItemStack item) {
        if (item.getItemMeta() == null) return false;

        return item.getItemMeta().getPersistentDataContainer().getKeys().contains(key);
    }

    public static String getNamed() {
        return "Core";
    }

    public final CoreLogger getCoreLogger(){
        return CoreLogger.getCoreLogger();
    }
}
