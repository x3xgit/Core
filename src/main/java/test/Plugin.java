package test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import technologycommunity.net.core.inventory.model.ItemCrafter;
import technologycommunity.net.core.plugin.Core;

import test.commands.GetBook;
import test.commands.GetMap;
import test.commands.Inventory;
import test.listener.MyItemListener;

public class Plugin extends Core {
    public ItemStack item = ItemCrafter.of(Material.FURNACE).setTitle("&9&k| &bSpells Book &9&k|").craft();

    @Override
    protected void onStart() {
        new GetBook().register();
        new MyItemListener(item).register();

        new GetMap().register();
        new Inventory().register();

        getCoreLogger().information("&bPlugin is started.");
    }

    @Override
    protected void onFinish() {
        getCoreLogger().information("&bPlugin is finished.");
    }

    public static Plugin getInstance() {
        return (Plugin) Core.getInstance();
    }
}
