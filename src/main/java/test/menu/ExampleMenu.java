package test.menu;

import org.bukkit.entity.Player;
import technologycommunity.net.core.inventory.Menu;

public class ExampleMenu extends Menu {
    public ExampleMenu(final Player player) {
        this.displayTo(player);
    }
}
