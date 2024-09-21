package test;

import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.plugin.Core;
import test.commands.GetMap;
import test.commands.Inventory;
import test.commands.VerifyTypes;

public class Plugin extends Core {
    @Override
    protected void onStart() {
        new GetMap().register();
        new Inventory().register();
        new VerifyTypes().register();

        getCoreLogger().log(Corelor.GREEN + "Plugin is started.");
    }

    @Override
    protected void onFinish() {
        getCoreLogger().log(Corelor.DARK_RED + "Plugin is finished.");
    }

    public static Plugin getInstance() {
        return (Plugin) Core.getInstance();
    }
}
