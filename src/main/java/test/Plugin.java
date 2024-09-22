package test;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.plugin.Core;

import test.commands.GetMap;
import test.commands.VerifyTypes;

public class Plugin extends Core {
    @Override
    protected void onStart() {
        new GetMap().register();
        // Inventory already has been registered.
        new VerifyTypes().register();

        getCoreLogger().log(Corelor.GREEN + "Plugin is started.");
    }

    public static Plugin getInstance() {
        return (Plugin) Core.getInstance();
    }
}
