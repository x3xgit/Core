package test;

import technologycommunity.net.core.plugin.Core;

import test.commands.GetMap;
import test.commands.Inventory;

public class Plugin extends Core {
    @Override
    protected void onStart() {
        new GetMap().register();
        new Inventory().register();

        getCoreLogger().information("Plugin is started.");
    }

    @Override
    protected void onFinish() {
        getLogger().info("Plugin is finished.");
    }

    public static Plugin getInstance() {
        return (Plugin) Core.getInstance();
    }
}
