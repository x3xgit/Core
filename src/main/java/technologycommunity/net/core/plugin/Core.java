package technologycommunity.net.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import technologycommunity.net.core.cooldown.Cooldown;
import technologycommunity.net.core.exception.CoreException;
import technologycommunity.net.core.menu.Menu;
import technologycommunity.net.core.logger.CoreLogger;

import java.io.File;
import java.util.Objects;

public class Core extends JavaPlugin {
    private static NamespacedKey key;
    private static Core instance;
    private static Class<? extends Core> parent;
    private static boolean registered = false;

    public static Core getInstance() {
        if (instance == null) {
            try {
                instance = JavaPlugin.getPlugin(Core.class);
            } catch (final IllegalStateException ex) {
                Core.getInstance().getCoreLogger().error("Failed to get instance of the plugin, you need to do a clean restart instead.");
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

        this.onStart();
    }

    @Override
    public final void onLoad() {
        this.onPreStart();
    }

    @Override
    public final void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            if (Menu.getPlayerMenu(onlinePlayer) != null) {
                Menu.rejectPlayerMenu(onlinePlayer);
                onlinePlayer.closeInventory();
            }

        this.onFinish();
    }

    protected final void initialize() {
        if (registered) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new CoreException("Core is already initialized.");
        } else {
            registered = true;
        }

        instance = this;
        key = new NamespacedKey(instance, "plugin");
        parent = this.getClass();

        Reflection.Scanner.scanAndRegister(Core.class);

        // new Listener().register();
        Cooldown.startRunnable();
    }

    public final void savePluginResource(final @NotNull String resourcePath, final boolean replace) {
        if (isResourceExist(resourcePath) && !replace)
            return;

        instance.saveResource(resourcePath, replace);
    }

    public final void savePluginResource(final @NotNull String resourcePath) {
        this.savePluginResource(resourcePath, false);
    }

    public final YamlConfiguration getPluginResource(final @NotNull String resourcePath) {
        final File configuration = new File(instance.getDataFolder(), resourcePath);

        if (!isResourceExist(resourcePath))
            savePluginResource(resourcePath);

        return YamlConfiguration.loadConfiguration(configuration);
    }

    protected static boolean isResourceExist(final @NotNull String resourcePath) {
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
        return Core.getInstance().getDescription().getName();
    }

    public final CoreLogger getCoreLogger(){
        return CoreLogger.getCoreLogger();
    }

    public static Class<? extends Core> getParent() {
        return parent;
    }

    protected static String getSource(Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    protected static String getSource() {
        return getSource(getParent());
    }

    public static @NotNull CommandMap getCommandMap() {
        return Bukkit.getServer().getCommandMap();
    }
}
